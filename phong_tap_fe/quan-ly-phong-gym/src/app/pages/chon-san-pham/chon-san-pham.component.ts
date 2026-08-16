import { CartService } from './../../../common/shared/service/application/cartService';
import { SanPhamGymService } from './../../../common/shared/service/application/sanPhamGymService';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { giamGia } from '../../../common/shared/enums/giamGia.enums';
import { firstValueFrom } from 'rxjs';
import { LabelValuePipe } from '../../../common/base/pipe/labelValue/labelValue.component';
import Swal from 'sweetalert2';
import { MoneyPipe } from '../../../common/base/pipe/moneny/moneyPipe.component';
import { InputMonenyComponent } from '../../../common/base/controls/input-moneny/input-moneny.component';

@Component({
  selector: 'app-chon-san-pham',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    LabelValuePipe,
    MoneyPipe,
    InputMonenyComponent,
  ],
  templateUrl: './chon-san-pham.component.html',
  styleUrls: ['./chon-san-pham.component.css'],
})
export class ChonSanPhamComponent implements OnInit {
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
  public selectedProduct: any = null;

  constructor(
    private fb: FormBuilder,
    private sanPhamGymService: SanPhamGymService,
    private cartService: CartService,
  ) {
    this.formSearch = this.fb.group({
      tenSanPham: [null],
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
      this.sanPhamGymService.getAllSanPham({
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

  closeDialogcd() {
    this.onClose.emit(null);
  }

  changeTab(val: any) {}
}
