import { CustomerService } from './../../../common/shared/service/application/customerService';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DialogService } from '../../../common/shared/service/base/dialogservice';
import { firstValueFrom } from 'rxjs';
import { NzModalRef } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-chon-khach-hang',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './chon-khach-hang.component.html',
  styleUrls: ['./chon-khach-hang.component.css'],
})
export class ChonKhachHangComponent implements OnInit {
  @Output() onClose = new EventEmitter<any | null>();
  @Output() chonCongDan = new EventEmitter<any>();

  public formSearch?: FormGroup;
  public isLoading?: boolean;
  public paging: any;
  public indexTab: number = 0;
  public listOfData: any[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private customerService: CustomerService,
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

    const res = await firstValueFrom(
      this.customerService.getAllKhachHang(params),
    );

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
