import {
  DialogService,
  DialogSize,
} from './../../../../common/shared/service/base/dialogservice';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DialogMode } from '../../../../common/shared/service/base/dialogservice';
import { InputSelectComponent } from '../../../../common/base/controls/input-select/input-select.component';
import { ChiTietHuanLuyenVienComponent } from './chi-tiet-huan-luyen-vien/chi-tiet-huan-luyen-vien.component';
import { huanLuyenVienOptions } from '../../../../common/shared/enums/huanLuyenVien.enums';
import { HuanLuyenVienService } from '../../../../common/shared/service/application/huanLuyenVienService';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-coach',
  standalone: true,
  imports: [FormModule, CommonModule, InputSelectComponent],
  templateUrl: './coach.component.html',
  styleUrls: ['./coach.component.css'],
})
export class CoachComponent implements OnInit {
  public formSearch?: FormGroup;
  public listOfData: any[] = [];
  public listHuanLuyenVien: any[] = huanLuyenVienOptions;
  public page = 0;
  public pageSize = 5;
  public totalPages = 0;
  public totalItems: number = 0;

  constructor(
    private dialogService: DialogService,
    private huanLuyenVienService: HuanLuyenVienService,
    private fb: FormBuilder,
  ) {
    this.formSearch = this.fb.group({
      id: [null],
      tenKhachHang: [null],
      soDienThoai: [null],
      idNguoiDung: [null],
      tenNguoiDung: [null],
      tenGoiTap: [null],
      vaiTro: [null],
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
      this.huanLuyenVienService.GetAllHuanLuyenVien({
        page: this.page,
        size: this.pageSize,
        ...this.formSearch.value,
      }),
    );

    this.listOfData = res.items;
  }

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    console.log('mode', mode);
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Huấn luyện viên cho khách hàng'
            : 'Thêm thông tin huấn luyện viên cho khách hàng';
        if (mode === 'edit')
          option.title = 'Cập nhật thông tin huấn luyện viên cho khách hàng';
        option.size = DialogSize.large;
        option.component = ChiTietHuanLuyenVienComponent;
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
      const response = await firstValueFrom(
        this.huanLuyenVienService.DeleteHuanLuyenVien(val),
      );

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

  async onReset() {
    this.formSearch?.reset();
    await this.getData();
  }

  changeTab(val: any) {}
}
