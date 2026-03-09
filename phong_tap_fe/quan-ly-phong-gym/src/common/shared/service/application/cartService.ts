import { AuthService } from './authService';
import { BehaviorSubject, Subscription } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItem: { product: any; soLuong: number }[] = [];
  private cartCurrentSubject = new BehaviorSubject<
    { product: any; soLuong: number }[]
  >([]);
  cartItem$ = this.cartCurrentSubject.asObservable();
  private userSubscription: Subscription;

  constructor(private authService: AuthService) {
    this.userSubscription = this.authService.currentUserSubject$.subscribe(
      (user) => {
        this.loadCart();
      },
    );
  }

  ngDestroy() {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  private getCartKey() {
    const TenKhachHang = this.authService.getUserCurrent();
    return TenKhachHang ? `cartItem_${TenKhachHang}` : 'cartItem_guest';
  }

  loadCart() {
    if (typeof window !== 'undefined' && window.localStorage) {
      const cartkey = this.getCartKey();
      const cartSave = localStorage.getItem(cartkey);
      if (cartSave) {
        this.cartItem = JSON.parse(cartSave);
      } else {
        this.cartItem = [];
      }
      this.cartCurrentSubject.next(this.cartItem);
    } else {
      this.cartItem = [];
      this.cartCurrentSubject.next(this.cartItem);
    }
  }

  getTotalPrice(): number {
    return this.cartItem.reduce((tong, item) => {
      const gia = item.product.giaSauGiam || item.product.gia;
      return tong + gia * item.soLuong;
    }, 0);
  }

  addToCart(product: any, soLuong: number = 1) {
    const soLuongCurrent = this.cartItem.find(
      (item) => item.product.id === product.id,
    );
    if (soLuongCurrent) {
      soLuongCurrent.soLuong += soLuong;
    } else {
      this.cartItem.push({ product, soLuong });
    }
    this.updateCart();
  }

  private updateCart() {
    const cartkey = this.getCartKey();
    localStorage.setItem(cartkey, JSON.stringify(this.cartItem));
    this.cartCurrentSubject.next(this.cartItem);
  }

  removeFromCart(productId: string) {
    this.cartItem = this.cartItem.filter(
      (item) => item.product.id !== productId,
    );
    this.updateCart();
  }

  updateSoLuong(productId: string, soLuong: number) {
    const item = this.cartItem.find((item) => item.product.id === productId);
    if (item && soLuong >= 1) {
      item.soLuong = soLuong;
      this.updateCart();
    }
  }

  clearCart() {
    this.cartItem = [];
    this.updateCart();
  }
}
