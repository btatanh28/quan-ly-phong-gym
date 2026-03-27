import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';

import { CommonModule } from '@angular/common';
import { vaiTroOptions } from '../../../../common/shared/enums/vaiTro.enums';
import { FormModule } from '../../../../common/module/forms.module';
import { AuthService } from '../../../../common/shared/service/application/authService';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from '../../../../common/shared/service/base/dialogservice';
import { UsersService } from '../../../../common/shared/service/application/userService';
import { CustomerService } from '../../../../common/shared/service/application/customerService';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';
import { MoneyPipe } from '../../../../common/base/pipe/moneny/moneyPipe.component';
import { LabelValuePipe } from '../../../../common/base/pipe/labelValue/labelValue.component';
import { InputSelectComponent } from '../../../../common/base/controls/input-select/input-select.component';
import { ChiTietNguoiDungComponent } from './chi-tiet-nguoi-dung/chi-tiet-nguoi-dung.component';
import { xacNhan } from '../../../../common/shared/enums/xacNhan.enums';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    MoneyPipe,
    LabelValuePipe,
    InputSelectComponent,
  ],
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],
})
export class AccountComponent implements OnInit {
  public users: any[] = [];
  public customer: any[] = [];
  public activeTab: string = 'Nhân Viên';
  public formSearch?: FormGroup;
  public editingUsers: boolean = false;
  public editingId: string | null = null;
  public listOfData: any[] = [];
  public listVaiTro: any[] = vaiTroOptions;
  public listOfDataKhachHang: any[] = [];
  public listXacNhan: any[] = xacNhan;

  selectTab(tab: string) {
    this.activeTab = tab;
  }

  constructor(
    private authService: AuthService,
    private dialogService: DialogService,
    private usersService: UsersService,
    private customerService: CustomerService,
    private fb: FormBuilder,
  ) {
    this.formSearch = this.fb.group({
      tenNguoiDung: [null],
      email: [null],
      soDienThoai: [null],
      diaChi: [null],
      ngayVaoLam: [null],
      cccd: [null],
      luong: [null],
      vaiTro: [null],
      tenKhachHang: [null],
    });
  }

  async ngOnInit() {
    await this.getData();
    await this.getDataKhachHang();
  }

  async getData() {
    if (!this.formSearch) return;

    const params = {
      ...this.formSearch.value,
    };

    const res = await firstValueFrom(this.usersService.getAllUsers(params));

    this.listOfData = res.items;
  }

  async getDataKhachHang() {
    if (!this.formSearch) return;

    const params = {
      ...this.formSearch.value,
    };

    const res = await firstValueFrom(
      this.customerService.getAllKhachHang(params),
    );

    this.listOfDataKhachHang = res.items;
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  async deleteData(val: any) {
    const result = await Swal.fire({
      title: 'Bạn có chắc chắn muốn xóa không?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Không',
      confirmButtonText: 'Có',
    });

    if (result.isConfirmed) {
      const response = await firstValueFrom(this.usersService.deleteUser(val));

      if (response) {
        Swal.fire({
          title: 'Deleted!',
          text: 'Xóa dữ liệu thành công',
          icon: 'success',
        });
      }
    }

    await this.getData();
  }

  async deleteDataKhachHang(val: any) {
    const result = await Swal.fire({
      title: 'Bạn có chắc chắn muốn xóa không?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Không',
      confirmButtonText: 'Có',
    });

    if (result.isConfirmed) {
      const response = await firstValueFrom(
        this.customerService.deleteKhachHang(val),
      );

      if (response) {
        Swal.fire({
          title: 'Deleted!',
          text: 'Xóa dữ liệu thành công',
          icon: 'success',
        });
      }
    }

    await this.getDataKhachHang();
  }

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    console.log('mode', mode);
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Xem chi tiết thông tin nhân viên'
            : 'Thêm thông tin nhân viên';
        if (mode === 'edit') option.title = 'Cập nhật thông tin nhân viên';
        option.size = DialogSize.large;
        option.component = ChiTietNguoiDungComponent;
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

  changeTabKhachHang(val: any) {}
}
