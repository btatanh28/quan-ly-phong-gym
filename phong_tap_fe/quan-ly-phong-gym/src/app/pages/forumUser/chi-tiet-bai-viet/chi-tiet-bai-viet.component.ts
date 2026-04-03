import { ProductService } from './../../../../common/shared/service/application/productService';
import { AuthService } from './../../../../common/shared/service/application/authService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { InputSelectApiComponent } from '../../../../common/base/controls/input-select-api/input-select-api.component';
import { FormModule } from '../../../../common/module/forms.module';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ForumService } from '../../../../common/shared/service/application/forumService';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { DialogMode } from '../../../../common/shared/service/base/dialogservice';
import { firstValueFrom } from 'rxjs';
import { ExtentionService } from '../../../../common/base/service/extention.service';
import { InputDanhGiaComponent } from '../../../../common/base/controls/input-danh-gia/input-danh-gia.component';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-chi-tiet-bai-viet',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputSelectApiComponent,
    InputDanhGiaComponent,
    RouterModule,
  ],
  templateUrl: './chi-tiet-bai-viet.component.html',
  styleUrls: ['./chi-tiet-bai-viet.component.css'],
})
export class ChiTietBaiVietComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public myForm?: FormGroup;
  public rating: number = 0;
  public hoveredRating: number = 0;
  public previewUrl: string | ArrayBuffer | null = null;

  constructor(
    private fb: FormBuilder,
    private forumService: ForumService,
    public productService: ProductService,
    private authService: AuthService,
    private ex: ExtentionService,
    private router: Router,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      idGoiTap: [null],
      tenGoiTap: [null],
      binhLuan: [null],
      danhGia: [null],
      idNguoiDung: [null],
      hinhAnh: [null],
    });
  }

  ngOnInit() {
    this.idKhachHang = this.authService.getUserCurrent()?.id;
  }

  private idKhachHang: string | null = null;
  async saveData() {
    const danhGiaValue = this.myForm?.get('danhGia')?.value;
    if (!danhGiaValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu đánh giá',

        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    if (!this.idKhachHang) {
      Swal.fire({
        icon: 'error',
        title: 'Yêu cầu đăng nhập',
        text: 'Xin vui lòng đăng nhập!',
      });
      this.closeDialog();
      this.router.navigate(['/login']);
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
      idNguoiDung: this.authService.getUserCurrent()?.id,
    };

    let response = null;

    if (this.mode === DialogMode.add) {
      response = await firstValueFrom(this.forumService.addForum(req));
    } else {
      response = await firstValueFrom(this.forumService.updateForum(req));
    }

    Swal.fire({
      title: 'Lưu dữ liệu thành công!',
      icon: 'success',
      draggable: false,
    });

    this.closeDialog(true);
  }

  imagesBase64: string[] = [];
  previewImages: string | null = null;

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = () => {
        this.previewUrl = reader.result;

        this.myForm?.patchValue({
          hinhAnh: reader.result, // 👈 gửi ảnh base64 trong JSON
        });
      };

      reader.readAsDataURL(file); // chuyển thành base64
    }
  }

  setRating(star: number) {
    this.rating = star;
    this.myForm?.get('danhGia')?.setValue(star);
  }

  hoverRating(star: number) {
    this.hoveredRating = star;
  }

  resetHover() {
    this.hoveredRating = 0;
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }
}
