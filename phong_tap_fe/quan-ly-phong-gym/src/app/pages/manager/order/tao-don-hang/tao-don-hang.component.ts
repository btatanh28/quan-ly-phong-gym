import { CartService } from './../../../../../common/shared/service/application/cartService';
import { SanPhamGymService } from './../../../../../common/shared/service/application/sanPhamGymService';
import { VnpayService } from './../../../../../common/shared/service/application/vnPayService';
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
import { firstValueFrom, Subscription } from 'rxjs';
import { API_CURRENT } from '../../../../../common/shared/service/application/api-base';
import Swal from 'sweetalert2';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';
import { InputSelectApiComponent } from '../../../../../common/base/controls/input-select-api/input-select-api.component';
import { hinhThucThanhToan } from '../../../../../common/shared/enums/hinhThucThanhToan.enums';
import { ChonKhachHangComponent } from '../../../chon-khach-hang/chon-khach-hang.component';
import { ChonGoiTapComponent } from '../../../chon-goi-tap/chon-goi-tap.component';
import { InputMonenyComponent } from '../../../../../common/base/controls/input-moneny/input-moneny.component';
import { ChonSanPhamComponent } from '../../../chon-san-pham/chon-san-pham.component';
import { MoneyPipe } from '../../../../../common/base/pipe/moneny/moneyPipe.component';
import { LabelValuePipe } from '../../../../../common/base/pipe/labelValue/labelValue.component';

@Component({
  selector: 'app-tao-don-hang',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputSelectComponent,

    MoneyPipe,
    LabelValuePipe,
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
  public cartItems: { product: any; soLuong: number }[] = [];

  constructor(
    private dialogService: DialogService,
    private fb: FormBuilder,
    private ex: ExtentionService,
    public productService: ProductService,
    public sanPhamGymService: SanPhamGymService,
    private donHangService: DonHangService,
    private momoService: MomoService,
    private vnpayService: VnpayService,
    private cartService: CartService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      hinhThucThanhToan: [null],
      soDienThoai: [null],
      email: [null],
      tenKhachHang: [null],
      idKhachHang: [null],
    });

    this.cartService.cartItem$.subscribe((item) => {
      this.cartItems = item;
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
    return this.cartItems.reduce((tong, item) => {
      const gia = item.product.giaSauGiam || item.product.gia;
      return tong + gia * item.soLuong;
    }, 0);
  }

  get chiTietDonHangs() {
    return this.myForm?.get('chiTietDonHangs') as FormArray;
  }

  async saveData() {
    try {
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

        chiTietDonHangs: this.cartItems.map((item) => ({
          idGoiTap: item.product.tenGoiTap ? item.product.id : null,

          idSanPham: item.product.tenSanPham ? item.product.id : null,

          soLuong: item.product.tenGoiTap ? item.soLuong : 0,

          soLuongSanPham: item.product.tenSanPham ? item.soLuong : 0,

          gia: item.product.gia,
        })),
      };

      let response = null;

      if (this.myForm?.get('hinhThucThanhToan')?.value === 3) {
        response = await firstValueFrom(this.donHangService.CreateDonHang(req));

        Swal.fire({
          title: 'Thanh Toán và đặt hàng thành công',
          icon: 'success',
          draggable: true,
        });

        this.cartService.clearCart();

        this.closeDialog(true);
      } else if (this.myForm?.get('hinhThucThanhToan')?.value === 2) {
        // 1. Tạo đơn hàng trước → trạng thái PENDING
        const order: any = await firstValueFrom(
          this.donHangService.CreateDonHang(req),
        );

        const orderId = order?.id || order?.maDonHang;

        //2. Gọi VnPay API
        const vnPayRes: any = await firstValueFrom(
          this.vnpayService.payVNPay(this.getTotalPrice(), orderId),
        );

        // 3. Chuyển sang VnPay
        if (vnPayRes?.payUrl) {
          window.location.href = vnPayRes.payUrl;

          this.cartService.clearCart();
        } else {
          Swal.fire('Lỗi', 'Không tạo được thanh toán VNPay', 'error');
        }
      } else if (this.myForm?.get('hinhThucThanhToan')?.value === 1) {
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

          this.cartService.clearCart();
        } else {
          Swal.fire('Lỗi', 'Không tạo được thanh toán MoMo', 'error');
        }
      }

      this.closeDialog(true);
    } catch (error: any) {
      if (error?.error?.message) {
        let message = 'Không thể tạo đơn hàng';

        message = error.error.message;

        Swal.fire({
          position: 'center',
          icon: 'error',
          title: 'Không thể đặt hàng',
          text: message,
          confirmButtonText: 'Ok',
        });
      }
    }
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

  chonGoiTap(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = mode === 'view' ? 'Chọn gói tập' : 'Chọn gói tập';
        if (mode === 'edit') option.title = 'Chọn gói tập';
        option.size = DialogSize.large;
        option.component = ChonGoiTapComponent;
        option.inputs = {
          id: item?.id,
          mode: mode,
          item: item,
          trangThai: item?.trangThai,
        };
      },
      async (eventName, eventValue) => {
        if (eventName === 'onClose') {
          this.dialogService.closeDialogById(dialog.id);

          if (eventValue) {
            await this.getData();
          }
        }
      },
    );
  }

  chonSanPham(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = mode === 'view' ? 'Chọn sản phẩm' : 'Chọn sản phẩm';
        if (mode === 'edit') option.title = 'Chọn sản phẩm';
        option.size = DialogSize.large;
        option.component = ChonSanPhamComponent;
        option.inputs = {
          id: item?.id,
          mode: mode,
          item: item,
          trangThai: item?.trangThai,
        };
      },
      async (eventName, eventValue) => {
        if (eventName === 'onClose') {
          this.dialogService.closeDialogById(dialog.id);

          if (eventValue) {
            await this.getData();
          }
        }
      },
    );
  }

  updateQuantity(item: { product: any; soLuong: number }) {
    this.cartService.updateSoLuong(item.product.id, item.soLuong);
  }

  removeFromCart(item: { product: any; soLuong: number }) {
    this.cartService.removeFromCart(item.product.id);
  }
}
