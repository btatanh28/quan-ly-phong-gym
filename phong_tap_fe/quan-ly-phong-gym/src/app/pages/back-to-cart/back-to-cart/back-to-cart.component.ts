import { CommonModule } from '@angular/common';
import { Component, NgZone, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { Route, Router, RouterModule } from '@angular/router';
import { CartService } from '../../../../common/shared/service/application/cartService';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-back-to-cart',
  standalone: true,
  imports: [CommonModule, FormModule, RouterModule],
  templateUrl: './back-to-cart.component.html',
  styleUrls: ['./back-to-cart.component.css'],
})
export class BackToCartComponent implements OnInit {
  public cartCount: number = 0;
  public hasCart: boolean = false;

  private cartSubscription?: Subscription;

  constructor(
    private cartService: CartService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.cartSubscription = this.cartService.cartItem$.subscribe(
      (cartItems) => {
        // Tổng số lượng sản phẩm trong giỏ
        this.cartCount = cartItems.reduce(
          (total, item) => total + item.soLuong,
          0,
        );

        // Kiểm tra giỏ hàng có sản phẩm hay không
        this.hasCart = cartItems.length > 0;
      },
    );
  }

  get isCartPage(): boolean {
    return this.router.url === '/cart';
  }

  ngOnDestroy(): void {
    this.cartSubscription?.unsubscribe();
  }
}
