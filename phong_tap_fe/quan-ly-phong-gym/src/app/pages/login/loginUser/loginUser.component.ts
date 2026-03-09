import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';

import { ChangePasswordComponent } from './change-password/change-password.component';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from '../../../../common/shared/service/base/dialogservice';
import { AuthService } from '../../../../common/shared/service/application/authService';
import { VerifyEmailComponent } from '../../verify-email/verify-email.component';

@Component({
  selector: 'app-loginUser',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    VerifyEmailComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './loginUser.component.html',
  styleUrls: ['./loginUser.component.css'],
})
export class LoginUserComponent {
  public myForm?: FormGroup;
  public showVerify = false;
  public emailForVerify!: string;

  constructor(
    private dialogService: DialogService,
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
  ) {
    this.myForm = this.fb.group({
      email: [null],
      matKhau: [null],
    });

    this.authService.checkTokenExpiration();

    // Nếu token hết hạn giữa phiên làm việc, redirect tự động
    this.authService.isLoggedIn$.subscribe((isLoggedIn) => {
      if (!isLoggedIn) {
        this.router.navigate(['/login']);
      }
    });
  }

  async onLoginSubmit() {
    const req = {
      ...this.myForm?.getRawValue(),
    };

    try {
      const response = await firstValueFrom(this.authService.login(req));
      if (response) {
        {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Đăng nhập thành công',
            showConfirmButton: false,
            timer: 2000,
          });
          if (this.authService.isAdmin() || this.authService.isEmployee()) {
            this.router.navigate(['manager']);
          } else {
            this.router.navigate(['/home']);
          }
        }
      }
    } catch (error: any) {
      this.emailForVerify = this.myForm?.get('email')?.value;
      if (error?.error?.message === 'Tài khoản chưa kích hoạt') {
        Swal.fire({
          icon: 'info',
          title: 'Tài khoản chưa kích hoạt',
          text: 'Vui lòng xác nhận email để kích hoạt tài khoản',
        });
        this.showVerify = true;
      }

      if (error?.error?.message === 'Sai mật khẩu') {
        Swal.fire({
          icon: 'warning',
          title: 'Tài khoản hoặc mật khẩu không đúng',
          text: 'Vui lòng thử lại',
        });
      }
    }
  }

  changePassWord(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title = mode === 'view' ? 'Quên mật khẩu' : 'Quên mật khẩu';
        option.size = DialogSize.small;
        option.component = ChangePasswordComponent;
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
        }
      },
    );
  }
}
