import { AuthService } from './../../../../../common/shared/service/application/authService';
import { PostService } from './../../../../../common/shared/service/application/postService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  DialogMode,
  DialogService,
} from '../../../../../common/shared/service/base/dialogservice';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExtentionService } from '../../../../../common/base/service/extention.service';
import { firstValueFrom } from 'rxjs';
import { API_CURRENT } from '../../../../../common/shared/service/application/api-base';
import Swal from 'sweetalert2';
import { FormModule } from '../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';

@Component({
  selector: 'app-chi-tiet-bai-dang',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './chi-tiet-bai-dang.component.html',
  styleUrls: ['./chi-tiet-bai-dang.component.css'],
})
export class ChiTietBaiDangComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public previewUrl: string | ArrayBuffer | null = null;
  public myForm?: FormGroup;
  public initForm: boolean = false;
  public indexTab: number = 0;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public modalImg: boolean = true;
  private idNguoiDung: string = '';
  messageService: any;

  constructor(
    private dialogService: DialogService,
    private postService: PostService,
    private fb: FormBuilder,
    private ex: ExtentionService,
    private authService: AuthService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      tenBaiViet: [null],
      noiDung: [null],
      hinhAnh: [null],
      ngayDangBai: [null],
      ngayCapNhat: [null],
      idNguoiDung: [null],
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
      this.postService.getPostById(this.id),
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
    const tenBaiVietValue = this.myForm?.get('tenBaiViet')?.value;
    if (!tenBaiVietValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu tên bài viết',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
      idNguoiDung: this.authService.getUserCurrent()?.id,
    };

    let response = null;

    if (this.mode === DialogMode.add) {
      response = await firstValueFrom(this.postService.addPost(req));
    } else {
      response = await firstValueFrom(this.postService.updatePost(req));
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
