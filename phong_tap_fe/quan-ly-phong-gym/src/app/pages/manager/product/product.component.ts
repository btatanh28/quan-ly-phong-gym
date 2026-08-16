import { ProductService } from './../../../../common/shared/service/application/productService';
import { AuthService } from './../../../../common/shared/service/application/authService';
import {
  DialogMode,
  DialogService,
  DialogSize,
} from './../../../../common/shared/service/base/dialogservice';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InputMonenyComponent } from '../../../../common/base/controls/input-moneny/input-moneny.component';
import { MoneyPipe } from '../../../../common/base/pipe/moneny/moneyPipe.component';
import {
  API_BASE,
  API_CURRENT,
} from '../../../../common/shared/service/application/api-base';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';
import { ChiTietSanPhamComponent } from './chi-tiet-san-pham/chi-tiet-san-pham.component';
import { LabelValuePipe } from '../../../../common/base/pipe/labelValue/labelValue.component';
import { thoiHanNgay } from '../../../../common/shared/enums/thoiHanNgay.enums';
import { giamGia } from '../../../../common/shared/enums/giamGia.enums';
import { ChiTietSanPhamGymComponent } from './chi-tiet-san-pham-gym/chi-tiet-san-pham-gym.component';
import { SanPhamGymService } from '../../../../common/shared/service/application/sanPhamGymService';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputMonenyComponent,
    MoneyPipe,
    LabelValuePipe,
  ],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  public formSearch?: FormGroup;
  public formSearchSanPham?: FormGroup;
  public listOfData: any[] = [];
  public listOfDataSanPham: any[] = [];
  public img_URl = API_CURRENT;
  public page = 0;
  public pageSize = 3;
  public totalPages = 0;
  public totalItems: number = 0;

  public pageSanPham = 0;
  public pageSizeSanPham = 3;
  public totalPagesSanPham = 0;
  public totalItemsSanPham: number = 0;

  public listThoiHanNgay: any[] = thoiHanNgay;
  public listGiamGia: any[] = giamGia;
  public activeTab: string = 'Gói tập';
  public loadedSanPham = false;

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private authService: AuthService,
    private productService: ProductService,
    private sanPhamGymService: SanPhamGymService,
  ) {
    this.formSearch = this.fb.group({
      id: [null],
      tenGoiTap: [null],
      gia: [null],
      hinhAnh: [null],
    });

    this.formSearchSanPham = this.fb.group({
      id: [null],
      tenSanPham: [null],
      gia: [null],
      soTonKho: [null],
      hinhAnh: [null],
    });
  }

  async ngOnInit() {
    await this.selectTab(this.activeTab);
  }

  async selectTab(tab: string) {
    this.activeTab = tab;

    if (tab === 'Gói tập') {
      await this.getData();
      this.loadedSanPham = false;
    }

    if (tab === 'Sản phẩm' && !this.loadedSanPham) {
      await this.getDataSanPham();
      this.loadedSanPham = true;
    }
  }

  async getData() {
    const response = await firstValueFrom(
      this.productService.getAllProduct({
        page: this.page,
        size: this.pageSize,
        ...this.formSearch?.value,
      }),
    );

    if (!this.formSearch) return;

    this.listOfData = response.items || [];
    this.totalItems = response.totalItems;
    this.totalPages = response.totalPages;
  }

  async getDataSanPham() {
    const res = await firstValueFrom(
      this.sanPhamGymService.getAllSanPham({
        page: this.pageSanPham,
        size: this.pageSizeSanPham,
        ...this.formSearchSanPham?.value,
      }),
    );

    if (!this.formSearchSanPham) return;

    this.listOfDataSanPham = res.items || [];
    this.totalItemsSanPham = res.totalItems;
    this.totalPagesSanPham = res.totalPages;
  }

  onPageChange(newPage: number) {
    if (newPage < 0 || newPage >= this.totalPages) return;
    this.page = newPage;

    this.getData();
  }

  onPageChangeSanPham(newPage: number) {
    if (newPage < 0 || newPage >= this.totalPagesSanPham) return;
    this.pageSanPham = newPage;

    this.getDataSanPham();
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
        this.productService.deleteProduct(val),
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

  async deleteDataSanPham(val: any) {
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
        this.sanPhamGymService.deleteSanPham(val),
      );

      if (response) {
        Swal.fire({
          title: 'Deleted!',
          text: 'Xóa dữ liệu thành công',
          icon: 'success',
        });
      }
    }

    await this.getDataSanPham();
  }

  handlerOpenDialog(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Xem chi tiết thông tin gói tập'
            : 'Thêm thông tin gói tập';
        if (mode === 'edit') option.title = 'Cập nhật thông tin gói tập';
        option.size = DialogSize.large;
        option.component = ChiTietSanPhamComponent;
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

  handlerOpenDialogSanPham(item: any = null, mode: string = DialogMode.add) {
    const dialog = this.dialogService.openDialog(
      (option) => {
        option.title =
          mode === 'view'
            ? 'Xem chi tiết thông tin sản phẩm'
            : 'Thêm thông tin sản phẩm';
        if (mode === 'edit') option.title = 'Cập nhật thông tin sản phẩm';
        option.size = DialogSize.large;
        option.component = ChiTietSanPhamGymComponent;
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
            await this.getDataSanPham();
          }
        }
      },
    );
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  async onReset(init: boolean = false) {
    this.formSearch?.reset();
    this.formSearchSanPham?.reset();

    if (!init) {
      await this.getData();
      await this.getDataSanPham();
    }
  }

  changeTab(val: any) {}
}
