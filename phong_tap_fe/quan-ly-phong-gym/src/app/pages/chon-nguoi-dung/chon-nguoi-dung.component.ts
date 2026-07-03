import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { UsersService } from '../../../common/shared/service/application/userService';
import { NzModalRef } from 'ng-zorro-antd/modal';
import { DialogService } from '../../../common/shared/service/base/dialogservice';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { vaiTroOptions } from '../../../common/shared/enums/vaiTro.enums';
import { LabelValuePipe } from "../../../common/base/pipe/labelValue/labelValue.component";

@Component({
  selector: 'app-chon-nguoi-dung',
  standalone: true,
  imports: [FormModule, CommonModule, LabelValuePipe],
  templateUrl: './chon-nguoi-dung.component.html',
  styleUrls: ['./chon-nguoi-dung.component.css'],
})
export class ChonNguoiDungComponent implements OnInit {
  @Output() onClose = new EventEmitter<any | null>();
  @Output() chonCongDan = new EventEmitter<any>();

  public formSearch?: FormGroup;
  public isLoading?: boolean;
  public paging: any;
  public indexTab: number = 0;
  public listOfData: any[] = [];
  public listVaiTro: any[] = vaiTroOptions;

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private userService: UsersService,
    private modalRef: NzModalRef,
  ) {
    this.formSearch = this.fb.group({
      tenKhachHang: [null],
      soDienThoai: [null],
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

    const res = await firstValueFrom(this.userService.getAllUsers(params));

    this.listOfData = res.items;
  }

  async onReset() {
    this.formSearch?.reset();
    await this.getData();
  }

  chon(item: any) {
    this.onClose.emit(item);
    this.modalRef.destroy();
  }

  closeDialogcd() {
    this.onClose.emit(null);
    this.modalRef.destroy();
  }

  changeTabKhachHang(val: any) {}
}
