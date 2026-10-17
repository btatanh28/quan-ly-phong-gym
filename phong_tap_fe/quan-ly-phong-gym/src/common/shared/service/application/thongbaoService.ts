import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ThongBaoService {
  private newOrderCountSubject = new BehaviorSubject<number>(
    Number(localStorage.getItem('newOrderCount') || 0),
  );

  newOrderCount$ = this.newOrderCountSubject.asObservable();

  increase() {
    const count = this.newOrderCountSubject.value + 1;

    localStorage.setItem('newOrderCount', count.toString());
    this.newOrderCountSubject.next(count);
  }

  clear() {
    localStorage.removeItem('newOrderCount');
    this.newOrderCountSubject.next(0);
  }

  getCount(): number {
    return this.newOrderCountSubject.value;
  }
}
