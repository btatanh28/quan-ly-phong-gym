import { TheTapService } from './../../../common/shared/service/application/theTapService';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from './../../../common/shared/service/base/dialogservice';
import { CommonModule, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FormModule } from '../../../common/module/forms.module';
import { IMAGE_CURRENT } from '../../../common/shared/service/application/api-base';
import { AuthService } from '../../../common/shared/service/application/authService';
import { DonHangService } from '../../../common/shared/service/application/donhangService';
import { ForumService } from '../../../common/shared/service/application/forumService';
import { CustomerService } from '../../../common/shared/service/application/customerService';
import { firstValueFrom } from 'rxjs';
import { DateTimeFormatPipe } from '../../../common/base/pipe/dateTimeFormat/dateTimeForm.component';
import { LabelValuePipe } from '../../../common/base/pipe/labelValue/labelValue.component';
import { trangThaiDonHang } from '../../../common/shared/enums/trangthaidonhang.enums';
import { MoneyPipe } from '../../../common/base/pipe/moneny/moneyPipe.component';
import { ChiTietDonHangComponent } from '../manager/order/chi-tiet-don-hang/chi-tiet-don-hang.component';
import { DateFormatPipe } from '../../../common/base/pipe/dateFormat/dateFormat.component';
import { ChinhSuaThongTinComponent } from './chinh-sua-thong-tin/chinh-sua-thong-tin.component';
import { QRCodeComponent } from 'angularx-qrcode';

@Component({
  selector: 'app-personal',
  standalone: true,
  imports: [
    NgFor,
    CommonModule,
    FormModule,
    DateTimeFormatPipe,
    LabelValuePipe,
    MoneyPipe,
    DateFormatPipe,
    QRCodeComponent,
  ],
  templateUrl: './personalPage.component.html',
  styleUrl: './personalPage.component.css',
})
export class PersonalPageComponent implements OnInit {
  public activeTab: string = 'Đơn Hàng';
  public donhang: any[] = [];
  public isModalOpen2: boolean = false;
  public selectedFile: File | null = null;
  public baseUrl = IMAGE_CURRENT;
  public userData: any[] = [];
  public listOfData: any[] = [];
  public forums: any[] = [];
  public currentUser: any;
  public listTrangThaiSanPham: any[] = trangThaiDonHang;

  public qrCode: string = '';
  public soNgayConLai: number = 0;

  constructor(
    private authService: AuthService,
    private donHangService: DonHangService,
    private dialogService: DialogService,
    private forumService: ForumService,
    private customerService: CustomerService,
    private theTapService: TheTapService,
  ) {}

  async ngOnInit() {
    this.currentUser = this.authService.getUserCurrent();
    await this.getDataUser();
    await this.getData('Đơn Hàng'); // chỉ load tab đầu
  }

  async getDataUser() {
    const params = {
      idKhachHang: this.currentUser?.id,
    };

    const uers = {
      id: this.currentUser?.id,
    };

    const userRes = await firstValueFrom(
      this.customerService.getKhachHangById(uers.id),
    );
    this.userData = [userRes];
  }

  async getData(tab?: string) {
    const params = {
      idKhachHang: this.currentUser?.id,
    };

    const uers = {
      id: this.currentUser?.id,
    };

    if (!tab || tab === 'Đơn Hàng') {
      const res = await firstValueFrom(
        this.donHangService.getDonHangByIdKhachHang(params.idKhachHang),
      );
      this.listOfData = res.items;
    }

    if (!tab || tab === 'Bài Đăng') {
      const response = await firstValueFrom(
        this.forumService.getForumByIdKhachHang(params.idKhachHang),
      );
      this.forums = response.items;
    }

    if (!tab || tab === 'Code') {
      const qr = await firstValueFrom(
        this.theTapService.getTheTapByKhachHang(uers.id),
      );
      this.qrCode = qr.data.qrCode;
      this.soNgayConLai = qr.data.soNgayConLai;
    }
  }

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view' ? 'Chỉnh sửa thông tin' : 'Chỉnh sửa thông tin';
        if (mode === 'edit') option.title = 'Chỉnh sửa thông tin';
        option.size = DialogSize.large;
        option.component = ChinhSuaThongTinComponent;
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

  async selectTab(tab: string) {
    this.activeTab = tab;

    await this.getData(tab);
  }
}
