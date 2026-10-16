import { CartService } from './../../../common/shared/service/application/cartService';
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
import Swal from 'sweetalert2';
import { MoneyPipe } from '../../../common/base/pipe/moneny/moneyPipe.component';
import { thoiHanNgay } from '../../../common/shared/enums/thoiHanNgay.enums';

@Component({
  selector: 'app-chon-goi-tap',
  standalone: true,
  imports: [FormModule, CommonModule, LabelValuePipe, MoneyPipe],
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
  public pageSize = 7;
  public totalPages = 0;
  public totalItems: number = 0;
  public listGiamGia: any[] = giamGia;
  public listThoiHanNgay: any[] = thoiHanNgay;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private cartService: CartService,
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

  async addToCart(product: any) {
    if (!product.id) {
      alert('Không tìm thấy Sản phẩm');
      return;
    }

    this.cartService.addToCart(product);
    Swal.fire({
      title: 'Thêm giỏ hàng thành công!',
      icon: 'success',
    });

    this.closeDialogcd();
  }

  async onReset() {
    this.formSearch?.reset();
    await this.getData();
  }

  closeDialogcd() {
    this.onClose.emit(null);
  }

  changeTab(val: any) {}
}
