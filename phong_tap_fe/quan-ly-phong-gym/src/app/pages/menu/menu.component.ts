import { CommonModule, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { FormModule } from '../../../common/module/forms.module';
import { MoneyPipe } from '../../../common/base/pipe/moneny/moneyPipe.component';
import { API_CURRENT } from '../../../common/shared/service/application/api-base';
import { ProductService } from '../../../common/shared/service/application/productService';
import { CartService } from '../../../common/shared/service/application/cartService';
import { DonHangService } from '../../../common/shared/service/application/donhangService';
import { firstValueFrom } from 'rxjs';
import { LabelValuePipe } from '../../../common/base/pipe/labelValue/labelValue.component';
import { giamGia } from '../../../common/shared/enums/giamGia.enums';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    RouterModule,
    NgFor,
    MoneyPipe,
    LabelValuePipe,
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
})
export class MenuComponent implements OnInit {
  public selectedCategory = 'all';
  public selectedProduct: any = null;
  public danhMucList: any[] = [];
  public selectedDanhMucId: String | null = null;
  public products: any[] = [];
  public filteredProducts: any[] = [];
  public keyword: string = '';
  public listGiamGia: any[] = giamGia;

  public totalItems: number = 0;
  public imgUrl = API_CURRENT;

  public page = 0;
  public pageSize = 6;
  public totalPages = 0;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private donhangService: DonHangService,
  ) {}

  async ngOnInit() {
    await this.getData();
  }

  async getData() {
    const response = await firstValueFrom(
      this.productService.getAllProduct({
        page: this.page,
        size: this.pageSize,
        idDanhMuc: this.selectedDanhMucId,
      }),
    );
    this.products = response.items || [];
    this.totalItems = response.totalItems;
    this.totalPages = response.totalPages;

    this.applyFilterAndPagination();
  }

  onPageChange(newPage: number) {
    if (newPage < 0 || newPage >= this.totalPages) return;
    this.page = newPage;
    this.getData();
  }

  async addToCart(product: any) {
    if (!product.id) {
      alert('Không tìm thấy Sản phẩm');
      return;
    }

    // const res = await firstValueFrom(
    //   this.productService.KiemTraTonKho(product.id),
    // );

    this.cartService.addToCart(product);
    Swal.fire({
      title: 'Thêm giỏ hàng thành công!',
      icon: 'success',
    });

    // if (res.soLuong > 0) {
    //   this.cartService.addToCart(product);
    //   Swal.fire({
    //     title: 'Thêm giỏ hàng thành công!',
    //     icon: 'success',
    //   });
    // } else {
    //   Swal.fire({
    //     title: 'Sản phẩm đã hết hàng!',
    //     icon: 'error',
    //   });
    // }
  }

  filterByDanhMuc(id: String | null) {
    this.selectedDanhMucId = id;
    this.page = 0; // reset về trang đầu
    // this.applyFilterAndPagination();
    this.getData();
  }

  applyFilterAndPagination() {
    if (this.selectedDanhMucId === null) {
      this.filteredProducts = [...this.products];
    } else {
      this.filteredProducts = this.products.filter(
        (p) => String(p.idDanhMuc) === String(this.selectedDanhMucId),
      );
    }
  }

  showDetails(product: any) {
    this.selectedProduct = { ...product };
  }

  closeModal() {
    this.selectedProduct = null;
  }
}
