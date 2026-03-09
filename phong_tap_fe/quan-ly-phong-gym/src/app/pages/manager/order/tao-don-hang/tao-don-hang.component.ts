import { MomoService } from './../../../../../common/shared/service/application/momoService';
import { DonHangService } from './../../../../../common/shared/service/application/donhangService';
import { CustomerService } from './../../../../../common/shared/service/application/customerService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { thoiHanNgay } from '../../../../../common/shared/enums/thoiHanNgay.enums';
import { giamGia } from '../../../../../common/shared/enums/giamGia.enums';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from '../../../../../common/shared/service/base/dialogservice';
import { ExtentionService } from '../../../../../common/base/service/extention.service';
import { ProductService } from '../../../../../common/shared/service/application/productService';
import { firstValueFrom } from 'rxjs';
import { API_CURRENT } from '../../../../../common/shared/service/application/api-base';
import Swal from 'sweetalert2';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';
import { InputSelectApiComponent } from '../../../../../common/base/controls/input-select-api/input-select-api.component';
import { hinhThucThanhToan } from '../../../../../common/shared/enums/hinhThucThanhToan.enums';
import { ChonKhachHangComponent } from '../../../chon-khach-hang/chon-khach-hang.component';
import { ChonGoiTapComponent } from '../../../chon-goi-tap/chon-goi-tap.component';
import { InputMonenyComponent } from '../../../../../common/base/controls/input-moneny/input-moneny.component';

@Component({
  selector: 'app-tao-don-hang',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputSelectComponent,
    InputSelectApiComponent,
    InputMonenyComponent,
  ],
  templateUrl: './tao-don-hang.component.html',
  styleUrls: ['./tao-don-hang.component.css'],
})
export class TaoDonHangComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public previewUrl: string | ArrayBuffer | null = null;
  public myForm?: FormGroup;
  public initForm: boolean = false;
  public indexTab: number = 0;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public modalImg: boolean = true;
  public listThoiHanNgay: any[] = thoiHanNgay;
  public listGiamGia: any[] = giamGia;
  public listHinhThucThanhToan: any[] = hinhThucThanhToan;

  constructor(
    private dialogService: DialogService,
    private fb: FormBuilder,
    private ex: ExtentionService,
    public productService: ProductService,
    private customerService: CustomerService,
    private donHangService: DonHangService,
    private momoService: MomoService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      idGoiTap: [null],
      tenGoiTap: [null],
      hinhThucThanhToan: [null],
      soDienThoai: [null],
      email: [null],
      tenKhachHang: [null],
      idKhachHang: [null],
      gia: [null],
      giamGia: [null],
      giaSauGiam: [null],
      chiTietDonHangs: this.fb.array([]),
    });
  }

  async ngOnInit() {
    if (this.id) {
      await this.getData();
    }

    await this.handleModeDialog();

    this.initForm = true;
  }

  async handleModeDialog() {
    const modeDisableForm = [DialogMode.view];

    if (this.mode && modeDisableForm.includes(this.mode as DialogMode)) {
      this.myForm?.disable();
    }

    if (this.mode === DialogMode.add || this.mode === DialogMode.edit) {
      this.viewButtonSave = true;
    }
  }

  async getData() {
    const response = await firstValueFrom(
      this.productService.getProductById(this.id),
    );
    if (response) {
      this.myForm?.patchValue(response);
      if (response.hinhAnh) {
        this.previewUrl = API_CURRENT + response.hinhAnh;
        this.modalImg = false;
      }
    }
  }

  getTotalPrice(): number {
    const items = this.myForm?.value?.chiTietDonHangs || [];

    return items.reduce((tong: number, item: any) => {
      const gia = item.giaSauGiam || item.gia;
      return tong + gia * item.soLuong;
    }, 0);
  }

  get chiTietDonHangs() {
    return this.myForm?.get('chiTietDonHangs') as FormArray;
  }

  async saveData() {
    const giaValue = this.myForm?.get('tenKhachHang')?.value;
    if (!giaValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu tên khách hàng',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
      chiTietDonHangs: this.chiTietDonHangs.value.map((item: any) => ({
        idGoiTap: item.idGoiTap,
        soLuong: item.soLuong,
        gia: item.gia,
      })),
    };

    let response = null;

    if (this.myForm?.get('hinhThucThanhToan')?.value === 1) {
      response = await firstValueFrom(this.donHangService.CreateDonHang(req));

      Swal.fire({
        title: 'Thanh Toán và đặt hàng thành công',
        icon: 'success',
        draggable: true,
      });

      this.closeDialog(true);
    } else if (this.myForm?.get('hinhThucThanhToan')?.value === 2) {
      // 1. Tạo đơn hàng trước → trạng thái PENDING
      const order: any = await firstValueFrom(
        this.donHangService.CreateDonHang(req),
      );

      const orderId = order?.id || order?.maDonHang;

      // 2. Gọi MoMo API
      const momoRes = await firstValueFrom(
        this.momoService.payMomo(this.getTotalPrice(), orderId),
      );

      // 3. Redirect sang MoMo
      if (momoRes && momoRes.payUrl) {
        window.location.href = momoRes.payUrl;
      } else {
        Swal.fire('Lỗi', 'Không tạo được thanh toán MoMo', 'error');
      }
    }

    this.closeDialog(true);
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }

  chonKhachHang() {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = 'Chọn khách hàng';
        option.size = DialogSize.medium;
        option.component = ChonKhachHangComponent;
      },
      (eventName, selectedData) => {
        if (selectedData) {
          const res = {
            idKhachHang: selectedData.id,
            tenKhachHang: selectedData.tenKhachHang,
            soDienThoai: selectedData.soDienThoai,
            email: selectedData.email,
          };

          this.myForm?.patchValue(res);
        }
      },
    );
  }

  chonGoiTap() {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = 'Chọn gọi tập';
        option.size = DialogSize.medium;
        option.component = ChonGoiTapComponent;
      },
      (eventName, selectedData) => {
        if (selectedData) {
          const res = {
            idGoiTap: selectedData.id,
            gia: selectedData.gia,
            giamGia: selectedData.giamGia,
            giaSauGiam: selectedData.giaSauGiam,
          };

          const item = this.fb.group({
            idGoiTap: [selectedData.id],
            soLuong: [1],
            gia: [selectedData.giaSauGiam || selectedData.gia],
          });

          this.chiTietDonHangs.push(item);

          this.myForm?.patchValue(res);
        }
      },
    );
  }
}
