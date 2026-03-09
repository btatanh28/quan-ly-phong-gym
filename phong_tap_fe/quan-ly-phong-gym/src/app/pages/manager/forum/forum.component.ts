import { ForumService } from './../../../../common/shared/service/application/forumService';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from '../../../../common/shared/service/base/dialogservice';
import { DateFormatPipe } from '../../../../common/base/pipe/dateFormat/dateFormat.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-forum',
  standalone: true,
  imports: [FormModule, CommonModule, DateFormatPipe],
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css'],
})
export class ForumComponent implements OnInit {
  public formSearch?: FormGroup;
  public listOfData: any[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private forumService: ForumService,
  ) {
    this.formSearch = this.fb.group({});
  }

  async ngOnInit() {
    await this.getData();
  }

  async getData() {
    if (!this.formSearch) return;

    const params = {
      ...this.formSearch.value,
    };

    const res = await firstValueFrom(this.forumService.getAllForum(params));

    this.listOfData = res.items;
  }

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    console.log('mode', mode);
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Xem chi tiết thông tin diễn đàn'
            : 'Thêm thông tin diễn đàn';
        if (mode === 'edit') option.title = 'Cập nhật thông tin diễn đàn';
        option.size = DialogSize.large;
        // option.component = ChiTietBaiDangComponent;
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
      const response = await firstValueFrom(this.forumService.deleteForum(val));

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

  async onReset(init: boolean = false) {
    this.formSearch?.reset();

    if (!init) {
      await this.getData();
    }
  }

  changeTab(val: any) {}
}
