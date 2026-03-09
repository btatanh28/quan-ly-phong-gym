import { CommonModule, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule } from '@angular/forms';
import { firstValueFrom, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { FormModule } from '../../../common/module/forms.module';
import { MoneyPipe } from '../../../common/base/pipe/moneny/moneyPipe.component';
import { InputSelectComponent } from '../../../common/base/controls/input-select/input-select.component';
import { hinhThucThanhToan } from '../../../common/shared/enums/hinhThucThanhToan.enums';
import { API_CURRENT } from '../../../common/shared/service/application/api-base';
import { ExtentionService } from '../../../common/base/service/extention.service';
import { CartService } from '../../../common/shared/service/application/cartService';
import { AuthService } from '../../../common/shared/service/application/authService';
import { DonHangService } from '../../../common/shared/service/application/donhangService';
import { MomoService } from '../../../common/shared/service/application/momoService';
import { LabelValuePipe } from "../../../common/base/pipe/labelValue/labelValue.component";
import { giamGia } from '../../../common/shared/enums/giamGia.enums';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [NgIf, CommonModule, FormModule, MoneyPipe, InputSelectComponent, LabelValuePipe],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit {
  public myForm?: FormGroup;
  public cartItems: { product: any; soLuong: number }[] = [];
  public isModalOpen: boolean = false;
  public selectedPaymentMethod!: { value: number; label: string };
  public paymentMethod = hinhThucThanhToan;
  public listGiamGia = giamGia;
  private idKhachHang: string | null = null;
  private soDienThoai: string | null = null;
  private currentUserSubscription!: Subscription;
  public baseUrl = API_CURRENT;

  constructor(
    private ex: ExtentionService,
    private fb: FormBuilder,
    private cartService: CartService,
    private authService: AuthService,
    private router: Router,
    private donHangService: DonHangService,
    private momoService: MomoService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      hinhThucThanhToan: [null],
      idKhachHang: [null],
      soDienThoai: [null],
      email: [null],
    });
    this.cartService.cartItem$.subscribe((item) => {
      this.cartItems = item;
    });
  }

  ngOnInit(): void {
    this.idKhachHang = this.authService.getUserCurrent()?.id;
    this.soDienThoai = this.authService.getUserCurrent()?.soDienThoai;

    if (!this.idKhachHang) {
      this.myForm?.patchValue({
        idKhachHang: this.idKhachHang,
        soDienThoai: this.soDienThoai,
      });
    }

    this.currentUserSubscription =
      this.authService.currentUserSubject$.subscribe((user) => {
        this.idKhachHang = user ? user.id : null;
        this.soDienThoai = user ? user.soDienThoai : null;

        this.myForm?.patchValue({
          idKhachHang: this.idKhachHang,
          soDienThoai: this.soDienThoai,
        });
      });
  }

  ngOnDestroy(): void {
    if (this.currentUserSubscription) {
      this.currentUserSubscription.unsubscribe();
    }
  }

  updateQuantity(item: { product: any; soLuong: number }) {
    this.cartService.updateSoLuong(item.product.id, item.soLuong);
  }

  removeFromCart(item: { product: any; soLuong: number }) {
    this.cartService.removeFromCart(item.product.id);
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((tong, item) => {
      const gia = item.product.giaSauGiam || item.product.gia;
      return tong + gia * item.soLuong;
    }, 0);
  }

  openCheckoutModal() {
    if (!this.idKhachHang) {
      Swal.fire({
        icon: 'error',
        title: 'Yêu cầu đăng nhập',
        text: 'Xin vui lòng đăng nhập!',
      });
      this.router.navigate(['/login']);
      return;
    }
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  async handleCheckout() {
    if (!this.idKhachHang) {
      Swal.fire({
        icon: 'error',
        title: 'Yêu cầu đăng nhập',
        text: 'Xin vui lòng đăng nhập!',
      });
      this.router.navigate(['/login']);
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
      chiTietDonHangs: this.cartItems.map((item) => ({
        idGoiTap: item.product.id,
        soLuong: item.soLuong,
        gia: item.product.gia,
      })),
    };

    let response = null;

    if (this.myForm?.get('hinhThucThanhToan')?.value === 1) {
      response = await firstValueFrom(this.donHangService.CreateDonHang(req));

      Swal.fire({
        title: 'Thanh Toán và đặt hàng thành công',
        icon: 'success',
        draggable: true,
      });

      this.closeModal();
      this.cartService.clearCart();
    } else if (this.myForm?.get('hinhThucThanhToan')?.value === 2) {
      // 1. Tạo đơn hàng trước → trạng thái PENDING
      const order: any = await firstValueFrom(
        this.donHangService.CreateDonHang(req),
      );

      const orderId = order?.id || order?.maDonHang;

      // 2. Gọi MoMo API
      const momoRes = await firstValueFrom(
        this.momoService.payMomo(this.getTotalPrice(), orderId),
      );

      // 3. Redirect sang MoMo
      if (momoRes && momoRes.payUrl) {
        window.location.href = momoRes.payUrl;
        this.closeModal();
        this.cartService.clearCart();
      } else {
        Swal.fire('Lỗi', 'Không tạo được thanh toán MoMo', 'error');
      }
    }
  }
}
