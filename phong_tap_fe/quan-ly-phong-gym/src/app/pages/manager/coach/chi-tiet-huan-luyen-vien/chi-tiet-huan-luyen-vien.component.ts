import { UsersService } from './../../../../../common/shared/service/application/userService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from '../../../../../common/shared/service/base/dialogservice';
import { ExtentionService } from '../../../../../common/base/service/extention.service';
import { ChonKhachHangTheTapComponent } from './chon-khach-hang-the-tap/chon-khach-hang-the-tap.component';
import { ChonNguoiDungComponent } from '../../../chon-nguoi-dung/chon-nguoi-dung.component';
import { vaiTroOptions } from '../../../../../common/shared/enums/vaiTro.enums';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';
import { HuanLuyenVienService } from '../../../../../common/shared/service/application/huanLuyenVienService';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-chi-tiet-huan-luyen-vien',
  standalone: true,
  imports: [FormModule, CommonModule, InputSelectComponent],
  templateUrl: './chi-tiet-huan-luyen-vien.component.html',
  styleUrls: ['./chi-tiet-huan-luyen-vien.component.css'],
})
export class ChiTietHuanLuyenVienComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public myForm?: FormGroup;
  public initForm: boolean = false;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public modalImg: boolean = true;
  public page = 0;
  public pageSize = 5;
  public totalPages = 0;
  public totalItems: number = 0;
  public listVaiTro: any[] = vaiTroOptions;

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private usersService: UsersService,
    private huanLuyenVienService: HuanLuyenVienService,
    private ex: ExtentionService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      idKhachHang: [null],
      idNguoiDung: [null],
      idGoiTap: [null],
      tenKhachHang: [null],
      soDienThoai: [null],
      soDienThoaiHLV: [null],
      emailHLV: [null],
      tenNguoiDung: [null],
      tenGoiTap: [null],
      vaiTro: [null],
      email: [null],
      ghiChu: [null],
      hinhAnh: [null],
    });
  }

  async ngOnInit() {
    this.initForm = true;

    await this.getData();

    await this.disableForm();
  }

  async disableForm() {
    this.myForm?.get('tenKhachHang')?.disable();
    this.myForm?.get('soDienThoai')?.disable();
    this.myForm?.get('soDienThoaiHLV')?.disable();
    this.myForm?.get('tenNguoiDung')?.disable();
    this.myForm?.get('tenGoiTap')?.disable();
    this.myForm?.get('vaiTro')?.disable();
    this.myForm?.get('emailHLV')?.disable();
  }

  async getData() {
    const response = await firstValueFrom(
      this.huanLuyenVienService.getHuanLuyenVienById(this.id),
    );
    if (response) {
      this.myForm?.patchValue(response);
    }
  }

  async saveData() {
    const tenKhachHangValue = this.myForm?.get('tenKhachHang')?.value;
    if (!tenKhachHangValue) {
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
    };

    let response = null;

    if (this.mode === DialogMode.add) {
      response = await firstValueFrom(
        this.huanLuyenVienService.CreateHuanLuyenVien(req),
      );
    } else {
      response = await firstValueFrom(
        this.huanLuyenVienService.UpdateHuanLuyenVien(req),
      );
    }

    Swal.fire({
      title: 'Lưu dữ liệu thành công!',
      icon: 'success',
      draggable: false,
    });

    this.closeDialog(true);
  }

  chonKhachHang() {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = 'Chọn khách hàng';
        option.size = DialogSize.medium;
        option.component = ChonKhachHangTheTapComponent;
      },
      (eventName, selectedData) => {
        if (selectedData) {
          const res = {
            idKhachHang: selectedData.idKhachHang,
            idGoiTap: selectedData.idGoiTap,
            tenKhachHang: selectedData.tenKhachHang,
            soDienThoai: selectedData.soDienThoai,
            tenGoiTap: selectedData.tenGoiTap,
          };

          this.myForm?.patchValue(res);
        }
      },
    );
  }

  chonHuanLuyenVien() {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = 'Chọn huấn luyện viên';
        option.size = DialogSize.medium;
        option.component = ChonNguoiDungComponent;
      },
      (eventName, selectedData) => {
        if (selectedData) {
          const res = {
            idNguoiDung: selectedData.id,
            tenNguoiDung: selectedData.tenNguoiDung,
            soDienThoaiHLV: selectedData.soDienThoai,
            emailHLV: selectedData.email,
            hinhAnh: selectedData.hinhAnh,
            vaiTro: selectedData.vaiTro,
          };

          this.myForm?.patchValue(res);
        }
      },
    );
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }
}
