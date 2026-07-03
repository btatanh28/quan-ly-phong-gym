import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { DialogService } from '../../../../../../common/shared/service/base/dialogservice';
import { CustomerService } from '../../../../../../common/shared/service/application/customerService';
import { NzModalRef } from 'ng-zorro-antd/modal';
import { FormModule } from '../../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chon-khach-hang-the-tap',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './chon-khach-hang-the-tap.component.html',
  styleUrls: ['./chon-khach-hang-the-tap.component.css'],
})
export class ChonKhachHangTheTapComponent implements OnInit {
  @Output() onClose = new EventEmitter<any | null>();
  @Output() chonCongDan = new EventEmitter<any>();

  public formSearch?: FormGroup;
  public isLoading?: boolean;
  public paging: any;
  public indexTab: number = 0;
  public listOfData: any[] = [];
  public page = 0;
  public pageSize = 5;
  public totalPages = 0;
  public totalItems: number = 0;

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
      page: this.page,
      size: this.pageSize,
      ...this.formSearch.value,
    };

    const res = await firstValueFrom(
      this.customerService.getKhachHangTheTap(params),
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

  changeTab(val: any) {}
}
