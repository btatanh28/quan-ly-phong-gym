import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ThongBaoService {
  private newOrderCountSubject = new BehaviorSubject<number>(0);

  newOrderCount$ = this.newOrderCountSubject.asObservable();

  isNewOrder(ngayMua: number): boolean {
    const now = Date.now();

    const thirtyMinutes = 30 * 60 * 1000;

    const diff = now - ngayMua;

    return diff >= 0 && diff <= thirtyMinutes;
  }

  updateNewOrderCount(orders: any[]): void {
    if (!Array.isArray(orders)) {
      this.newOrderCountSubject.next(0);
      return;
    }

    const newOrders = orders.filter((order) => {
      return order?.ngayMua && this.isNewOrder(Number(order.ngayMua));
    });

    console.log('Tổng đơn:', orders.length);
    console.log('Đơn trong 30 phút:', newOrders.length);

    this.newOrderCountSubject.next(newOrders.length);
  }

  // increase() {
  //   const count = this.newOrderCountSubject.value + 1;

  //   localStorage.setItem('newOrderCount', count.toString());
  //   this.newOrderCountSubject.next(count);
  // }

  clear(): void {
    this.newOrderCountSubject.next(0);
  }

  getCount(): number {
    return this.newOrderCountSubject.value;
  }
}
