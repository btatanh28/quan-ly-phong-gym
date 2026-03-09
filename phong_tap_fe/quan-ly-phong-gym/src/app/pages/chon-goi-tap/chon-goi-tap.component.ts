import { ProductService } from './../../../common/shared/service/application/productService';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DialogService } from '../../../common/shared/service/base/dialogservice';
import { NzModalRef } from 'ng-zorro-antd/modal';
import { firstValueFrom } from 'rxjs';
import { giamGia } from '../../../common/shared/enums/giamGia.enums';
import { LabelValuePipe } from '../../../common/base/pipe/labelValue/labelValue.component';

@Component({
  selector: 'app-chon-goi-tap',
  standalone: true,
  imports: [FormModule, CommonModule, LabelValuePipe],
  templateUrl: './chon-goi-tap.component.html',
  styleUrls: ['./chon-goi-tap.component.css'],
})
export class ChonGoiTapComponent implements OnInit {
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
  public listGiamGia: any[] = giamGia;

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private productService: ProductService,
    private modalRef: NzModalRef,
  ) {
    this.formSearch = this.fb.group({
      tenGoiTap: [null],
      gia: [null],
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
      this.productService.getAllProduct({
        page: this.page,
        size: this.pageSize,
        ...this.formSearch.value,
      }),
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
