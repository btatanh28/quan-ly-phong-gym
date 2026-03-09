import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';
import { AuthService } from '../../../../../common/shared/service/application/authService';
import { FormModule } from '../../../../../common/module/forms.module';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, FormModule],
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();
  public myForm?: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
  ) {
    this.myForm = this.fb.group({
      email: [null],
      matKhau: [null],
      xacNhanMatKhau: [null],
    });
  }

  ngOnInit() {}

  async onConfirm() {
    const req = {
      ...this.myForm?.getRawValue(),
    };

    try {
      const response = await firstValueFrom(
        this.authService.changePassWord(req),
      );
      if (response) {
        {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Đổi mật khẩu thành công',
            showConfirmButton: false,
            timer: 2000,
          });
        }
      }
      this.closeDialog(true);
    } catch (error: any) {
      if (error?.error?.message === 'Mật khẩu xác nhận không khớp') {
        Swal.fire({
          icon: 'info',
          title: 'Mật khẩu xác nhận không khớp',
          text: 'Vui lòng thử lại',
        });
      }
    }
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }
}
