import { CommentService } from './../../../common/shared/service/application/commentService';
import { AuthService } from './../../../common/shared/service/application/authService';
import { ExtentionService } from './../../../common/base/service/extention.service';
import { ProductService } from './../../../common/shared/service/application/productService';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from '../../../common/shared/service/base/dialogservice';
import { firstValueFrom } from 'rxjs';
import { ForumService } from '../../../common/shared/service/application/forumService';
import { IMAGE_CURRENT } from '../../../common/shared/service/application/api-base';
import { DateFormatPipe } from '../../../common/base/pipe/dateFormat/dateFormat.component';
import { ChiTietBaiVietComponent } from './chi-tiet-bai-viet/chi-tiet-bai-viet.component';
import { InputSelectApiComponent } from '../../../common/base/controls/input-select-api/input-select-api.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { InputDanhGiaComponent } from '../../../common/base/controls/input-danh-gia/input-danh-gia.component';

@Component({
  selector: 'app-forumUser',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    DateFormatPipe,
    InputSelectApiComponent,
    InputDanhGiaComponent,
  ],
  templateUrl: './forumUser.component.html',
  styleUrls: ['./forumUser.component.css'],
})
export class ForumUserComponent implements OnInit {
  public myForm?: FormGroup;
  public eidtForm?: FormGroup;
  public editComment?: FormGroup;
  public listOfData: any[] = [];
  public errorMessage: string | null = null;
  public baseUrl = IMAGE_CURRENT;
  public showEditForm: { [key: string]: boolean } = {};
  public showEditComment: { [key: string]: boolean } = {};
  public rating: number = 0;
  public hoveredRating: number = 0;
  public previewUrl: string | ArrayBuffer | null = null;
  public editingCommentId: string | null = null;

  constructor(
    private dialogService: DialogService,
    private forumService: ForumService,
    public productService: ProductService,
    private fb: FormBuilder,
    private ex: ExtentionService,
    private authService: AuthService,
    private commentService: CommentService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      noiDung: [null],
    });

    this.eidtForm = this.fb.group({
      id: [null],
      binhLuan: [null],
      idGoiTap: [null],
      danhGia: [null],
      hinhAnh: [null],
    });

    this.editComment = this.fb.group({
      id: [null],
      noiDung: [null],
    });
  }

  async ngOnInit() {
    this.currentUser = this.authService.getUserCurrent();
    await this.getData();
  }

  async getData() {
    const res = await firstValueFrom(this.forumService.getAllForum({}));

    this.listOfData = res.items;

    for (let post of this.listOfData) {
      const cmtRes = await firstValueFrom(
        this.commentService.getAllComment(post.id),
      );

      post.comments = cmtRes.items || [];
    }

    this.listOfData = res.items;
  }

  async saveData(id: String) {
    if (!this.myForm?.value.noiDung) {
      Swal.fire('Vui lòng nhập nội dung bình luận');
      return;
    }

    const user = this.authService.getUserCurrent();

    const req = {
      ...this.myForm?.getRawValue(),
      idDanhGia: id,
    };

    if (user?.role === 7) {
      req.idKhachHang = user.id;
      req.idNguoiDung = null;
    }

    if (user?.role === 2 || user?.role === 4) {
      req.idNguoiDung = user.id;
      req.idKhachHang = null;
    }

    let response = null;

    response = await firstValueFrom(this.commentService.addComment(req));

    Swal.fire({
      title: 'Gửi thành công!',
      icon: 'success',
      draggable: false,
    });

    this.myForm?.reset();
    await this.getData();
  }

  async upDateData(id: string) {
    const user = this.authService.getUserCurrent();

    const req = {
      ...this.eidtForm?.getRawValue(),
      idDanhGia: id,
    };

    if (user?.role === 7) {
      req.idKhachHang = user.id;
      req.idNguoiDung = null;
    }

    if (user?.role === 2 || user?.role === 4) {
      req.idNguoiDung = user.id;
      req.idKhachHang = null;
    }

    let response = null;

    response = await firstValueFrom(this.forumService.updateForum(req));

    Swal.fire({
      title: 'Gửi thành công!',
      icon: 'success',
      draggable: false,
    });

    this.showEditForm[id] = false;
    this.eidtForm?.reset();
    await this.getData();
  }

  async updateComment() {
    const user = this.authService.getUserCurrent();

    const req = {
      ...this.editComment?.getRawValue(),
      id: this.editingCommentId,
    };

    if (user?.role === 7) {
      req.idKhachHang = user.id;
      req.idNguoiDung = null;
    }

    if (user?.role === 2 || user?.role === 4) {
      req.idNguoiDung = user.id;
      req.idKhachHang = null;
    }

    let response = null;

    response = await firstValueFrom(this.commentService.updateComment(req));

    Swal.fire({
      title: 'Gửi thành công!',
      icon: 'success',
      draggable: false,
    });

    if (this.editingCommentId) {
      this.showEditComment[this.editingCommentId] = false;
    }

    this.editComment?.reset();
    this.editingCommentId = null;

    await this.getData();
  }

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Xem chi tiết thông tin diễn đàn'
            : 'Diễn đàn đánh giá';
        if (mode === 'edit') option.title = 'Cập nhật thông tin diễn đàn';
        option.size = DialogSize.medium;
        option.component = ChiTietBaiVietComponent;
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

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = () => {
        this.previewUrl = reader.result;

        this.eidtForm?.patchValue({
          hinhAnh: reader.result, // 👈 gửi ảnh base64 trong JSON
        });
      };

      reader.readAsDataURL(file); // chuyển thành base64
    }
  }

  closeDialog(id: string) {
    this.showEditComment[id] = false;
  }

  async showEidtComment(item: any) {
    this.editingCommentId = item.id;
    this.showEditComment[item.id] = true;

    const res = await firstValueFrom(
      this.commentService.getCommentById(item.id),
    );

    const comment = res?.data || res;

    this.editComment?.reset();
    this.editComment?.patchValue({
      noiDung: comment.noiDung,
    });
  }

  async showEidtForm(postId: string) {
    this.showEditForm[postId] = !this.showEditForm[postId];

    if (!this.showEditForm[postId]) return;

    const res = await firstValueFrom(this.forumService.getForumById(postId));

    const post = res?.data || res;

    setTimeout(() => {
      this.eidtForm?.reset();

      this.eidtForm?.patchValue({
        id: post.id,
        idGoiTap: post.idGoiTap,
        danhGia: post.danhGia,
        binhLuan: post.binhLuan,
        hinhAnh: post.hinhAnh,
      });

      this.previewUrl = post.hinhAnh ? this.baseUrl + post.hinhAnh : null;

      this.rating = post.danhGia || 0;
    });
  }

  currentUser: any;
  canEditPost(post: any): boolean {
    const user = this.currentUser;
    if (!user || !post) return false;

    if (user.role === 7) {
      return post.idKhachHang === user.id;
    }

    if (user.role === 1 || user.role === 4) {
      return post.idNguoiDung === user.id;
    }

    return false;
  }

  canEditComment(comment: any): boolean {
    const user = this.currentUser;
    if (!user || !comment) return false;

    if (user.role === 7) {
      return comment.idKhachHang === user.id;
    }

    if (user.role === 1 || user.role === 4) {
      return comment.idNguoiDung === user.id;
    }

    return false;
  }
}
