import { AuthService } from './../../../../common/shared/service/application/authService';
import {
  DialogService,
  DialogSize,
} from './../../../../common/shared/service/base/dialogservice';
import { Component, Input, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DialogMode } from '../../../../common/shared/service/base/dialogservice';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';
import { InputDateComponent } from '../../../../common/base/controls/input-date/input-date.component';
import { DateFormatPipe } from '../../../../common/base/pipe/dateFormat/dateFormat.component';
import { ChiTietBaiDangComponent } from './chi-tiet-bai-dang/chi-tiet-bai-dang.component';
import { PostService } from '../../../../common/shared/service/application/postService';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputDateComponent,
    InputDateComponent,
    DateFormatPipe,
  ],
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  public formSearch?: FormGroup;
  public listOfData: any[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private postService: PostService,
    private authService: AuthService,
  ) {
    this.formSearch = this.fb.group({
      id: [null],
      idNguoiDung: [null],
      tenNguoiDung: [null],
      tenBaiViet: [null],
      ngayCapNhat: [null],
      ngayDangBai: [null],
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

    const res = await firstValueFrom(this.postService.getAllPost(params));

    this.listOfData = res.items;
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
      const response = await firstValueFrom(this.postService.deletePost(val));

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

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    console.log('mode', mode);
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Xem chi tiết thông tin bài đăng'
            : 'Thêm thông tin bài đăng';
        if (mode === 'edit') option.title = 'Cập nhật thông tin bài đăng';
        option.size = DialogSize.large;
        option.component = ChiTietBaiDangComponent;
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
}
