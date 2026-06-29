import { TheTapService } from './../../../../common/shared/service/application/theTapService';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from './../../../../common/shared/service/base/dialogservice';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-exercise-card',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './exercise-card.component.html',
  styleUrls: ['./exercise-card.component.css'],
})
export class ExerciseCardComponent implements OnInit {
  public formSearch?: FormGroup;
  public listOfData: any[] = [];
  public page = 0;
  public pageSize = 5;
  public totalPages = 0;
  public totalItems: number = 0;

  constructor(
    private dialogService: DialogService,
    private fb: FormBuilder,
    private theTapService: TheTapService,
  ) {
    this.formSearch = fb.group({
      id: [null],
      tenKhachHang: [null],
      soDienThoai: [null],
      email: [null],
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

    const res = await firstValueFrom(
      this.theTapService.getAllTheTap({
        page: this.page,
        size: this.pageSize,
        ...this.formSearch.value,
      }),
    );

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
      const response = await firstValueFrom(
        this.theTapService.deleteTheTap(val),
      );

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
            ? 'Xem chi tiết thông tin nhân viên'
            : 'Thêm thông tin nhân viên';
        if (mode === 'edit') option.title = 'Cập nhật thông tin nhân viên';
        option.size = DialogSize.large;
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

  async onReset() {
    this.formSearch?.reset();
    await this.getData();
  }

  changeTab(val: any) {}
}
