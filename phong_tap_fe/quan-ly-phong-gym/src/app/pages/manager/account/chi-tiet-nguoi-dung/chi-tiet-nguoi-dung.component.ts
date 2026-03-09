import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  DialogMode,
  DialogService,
} from '../../../../../common/shared/service/base/dialogservice';
import { AuthService } from '../../../../../common/shared/service/application/authService';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UsersService } from '../../../../../common/shared/service/application/userService';
import { ExtentionService } from '../../../../../common/base/service/extention.service';
import { vaiTroOptions } from '../../../../../common/shared/enums/vaiTro.enums';
import { FormModule } from '../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';
import { firstValueFrom } from 'rxjs';
import { API_CURRENT } from '../../../../../common/shared/service/application/api-base';
import Swal from 'sweetalert2';
import { InputDateComponent } from '../../../../../common/base/controls/input-date/input-date.component';
import { InputMonenyComponent } from '../../../../../common/base/controls/input-moneny/input-moneny.component';

@Component({
  selector: 'app-chi-tiet-nguoi-dung',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputSelectComponent,
    InputDateComponent,
    InputMonenyComponent,
  ],
  templateUrl: './chi-tiet-nguoi-dung.component.html',
  styleUrls: ['./chi-tiet-nguoi-dung.component.css'],
})
export class ChiTietNguoiDungComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public previewUrl: string | ArrayBuffer | null = null;
  public myForm?: FormGroup;
  public initForm: boolean = false;
  public indexTab: number = 0;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public listVaiTro: any[] = vaiTroOptions;
  public modalImg: boolean = true;
  messageService: any;

  constructor(
    private dialogService: DialogService,
    private authService: AuthService,
    private fb: FormBuilder,
    private usersService: UsersService,
    private ex: ExtentionService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      tenNguoiDung: [null],
      email: [null],
      soDienThoai: [null],
      diaChi: [null],
      ngayVaoLam: [null],
      cccd: [null],
      luong: [null],
      vaiTro: [null],
      hinhAnh: [null],
      matKhau: ['123456'],
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
      this.usersService.getUserById(this.id),
    );

    if (response) {
      this.myForm?.patchValue(response);

      if (response.hinhAnh) {
        this.previewUrl = API_CURRENT + response.hinhAnh;
        this.modalImg = false;
      }
    }
  }

  async saveData() {
    const cccdValue = this.myForm?.get('cccd')?.value;
    if (!cccdValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu căn cước công dân',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const soDienThoaiValue = this.myForm?.get('soDienThoai')?.value;
    if (!soDienThoaiValue || soDienThoaiValue.length > 10) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Số điện thoại không hợp lệ',
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
      response = await firstValueFrom(this.authService.register(req));
    } else {
      response = await firstValueFrom(this.usersService.updateUser(req));
    }

    Swal.fire({
      title: 'Lưu dữ liệu thành công!',
      icon: 'success',
      draggable: false,
    });

    this.closeDialog(true);
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = () => {
        this.previewUrl = reader.result;

        this.myForm?.patchValue({
          hinhAnh: reader.result, // 👈 gửi ảnh base64 trong JSON
        });

        this.modalImg = false;
      };

      reader.readAsDataURL(file); // chuyển thành base64
      this.modalImg = false;
    }
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }
}
