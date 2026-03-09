import { ChiTietDonHangService } from './../../../../common/shared/service/application/chiTietDonHang';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from './../../../../common/shared/service/base/dialogservice';
import { DonHangService } from './../../../../common/shared/service/application/donhangService';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { MoneyPipe } from '../../../../common/base/pipe/moneny/moneyPipe.component';
import { LabelValuePipe } from '../../../../common/base/pipe/labelValue/labelValue.component';
import { DateTimeFormatPipe } from '../../../../common/base/pipe/dateTimeFormat/dateTimeForm.component';
import { DateFormatPipe } from '../../../../common/base/pipe/dateFormat/dateFormat.component';
import { trangThaiDonHang } from '../../../../common/shared/enums/trangthaidonhang.enums';
import { hinhThucThanhToan } from '../../../../common/shared/enums/hinhThucThanhToan.enums';
import { InputSelectComponent } from '../../../../common/base/controls/input-select/input-select.component';
import { ChiTietDonHangComponent } from './chi-tiet-don-hang/chi-tiet-don-hang.component';
import { XacNhanDonHangComponent } from './xac-nhan-don-hang/xac-nhan-don-hang.component';
import { TaoDonHangComponent } from './tao-don-hang/tao-don-hang.component';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    MoneyPipe,
    InputSelectComponent,
    DateTimeFormatPipe,
    DateFormatPipe,
  ],
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css'],
})
export class OrderComponent implements OnInit {
  public formSearch?: FormGroup;
  public page = 0;
  public pageSize = 5;
  public totalPages = 0;
  public totalItems: number = 0;
  public listOfData: any[] = [];
  public listTrangThaiDonHang: any[] = trangThaiDonHang;
  public listHinhThucThanhToan: any[] = hinhThucThanhToan;

  constructor(
    private fb: FormBuilder,
    private donHangService: DonHangService,
    private dialogService: DialogService,
    private chiTietDonHangService: ChiTietDonHangService,
  ) {
    this.formSearch = this.fb.group({
      idKhachHang: [null],
      tenKhachHang: [null],
      soDienThoai: [null],
      email: [null],
      trangThaiSanPham: [null],
      hinhThucThanhToan: [null],
      tenNguoiDung: [null],
      idNguoiDung: [null],
      ngayCapNhat: [null],
    });
  }

  async ngOnInit() {
    await this.getData();
  }

  async getData() {
    if (!this.formSearch) return;

    const params = {
      ...this.formSearch.value,
    };

    const res = await firstValueFrom(
      this.donHangService.GetAllDonHang({
        page: this.page,
        size: this.pageSize,
        ...this.formSearch.value,
      }),
    );

    this.listOfData = res.items;

    for (let item of this.listOfData) {
      const chiTiet = await firstValueFrom(
        this.chiTietDonHangService.getChiTietDonHangId(item.id),
      );

      item.chiTietDonHangs = chiTiet;
    }
  }

  handlerOpenDialogChiTietDonHang(
    item: any = null,
    mode: string = DialogMode.add,
  ) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view' ? 'Xem chi tiết đơn hàng' : 'Thêm thông tin sản phẩm';
        if (mode === 'edit') option.title = 'Cập nhật thông tin sản phẩm';
        option.size = DialogSize.small;
        option.component = ChiTietDonHangComponent;
        option.inputs = {
          id: item?.id,
          mode: mode,
          item: item,
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

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view' ? 'Xác nhận đơn hàng' : 'Xác nhận đơn hàng';
        if (mode === 'edit') option.title = 'Xác nhận đơn hàng';
        option.size = DialogSize.large;
        option.component = XacNhanDonHangComponent;
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

  handlerOpenDialogDonHang(item: any = null, mode: string = DialogMode.add) {
    console.log('mode', mode);
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view' ? 'Xem chi tiết thông tin nhân viên' : 'Tạo đơn hàng';
        if (mode === 'edit') option.title = 'Cập nhật thông tin nhân viên';
        option.size = DialogSize.large;
        option.component = TaoDonHangComponent;
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

  async onReset(init: boolean = false) {
    this.formSearch?.reset();

    if (!init) {
      await this.getData();
    }
  }

  changeTab(val: any) {}
}
