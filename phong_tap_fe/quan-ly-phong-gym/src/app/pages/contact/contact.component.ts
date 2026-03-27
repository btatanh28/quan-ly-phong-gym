import { ContactService } from './../../../common/shared/service/application/lienheService';
import { AuthService } from './../../../common/shared/service/application/authService';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExtentionService } from '../../../common/base/service/extention.service';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
})
export class ContactComponent implements OnInit {
  public myForm?: FormGroup;
  public currentUser: any;
  constructor(
    private fb: FormBuilder,
    private ex: ExtentionService,
    private authService: AuthService,
    private contactService: ContactService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      tenKhachHang: [null],
      soDienThoai: [null],
      email: [null],
      noiDung: [null],
    });
  }

  ngOnInit() {
    this.currentUser = this.authService.getUserCurrent();

    if (this.currentUser) {
      this.myForm?.get('tenKhachHang')?.patchValue(this.currentUser.name);
      this.myForm?.get('soDienThoai')?.patchValue(this.currentUser.soDienThoai);
    }
  }

  async saveData() {
    const soDienThoaiValue = this.myForm?.get('soDienThoai')?.value;
    if (!soDienThoaiValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu số điện thoại',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    if (
      this.myForm?.get('soDienThoai')?.value.length > 10 ||
      this.myForm?.get('soDienThoai')?.value.length <= 9
    ) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Số điện thoại không hợp lệ',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const noiDungValue = this.myForm?.get('noiDung')?.value;
    if (!noiDungValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu nội dung',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
      idKhachHang: this.authService.getUserCurrent()?.id,
    };

    let response = null;

    response = await firstValueFrom(this.contactService.createLienHe(req));

    Swal.fire({
      title: 'Gửi liên hệ thành công!',
      icon: 'success',
      draggable: false,
    });

    this.myForm?.reset();
  }
}
