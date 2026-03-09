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
  public listOfData: any[] = [];
  public img_URl = API_CURRENT;
  public page = 0;
  public pageSize = 5;
  public totalPages = 0;
  public totalItems: number = 0;
  public listThoiHanNgay: any[] = thoiHanNgay;
  public listGiamGia: any[] = giamGia;

  constructor(
    private fb: FormBuilder,
    private dialogService: DialogService,
    private authService: AuthService,
    private productService: ProductService,
  ) {
    this.formSearch = this.fb.group({
      id: [null],
      tenGoiTap: [null],
      gia: [null],
      hinhAnh: [null],
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

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  async onReset(init: boolean = false) {
    this.formSearch?.reset();

    if (!init) {
      await this.getData();
    }
  }

  changeTab(val: any) {}
}
