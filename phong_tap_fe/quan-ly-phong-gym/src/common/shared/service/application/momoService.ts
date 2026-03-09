import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';
@Injectable({
  providedIn: 'root',
})
export class MomoService {
  private apiUrl = `${API_CURRENT}`;

  constructor(private http: HttpClient) {}

  payMomo(amount: number, orderId: string): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}/momo/pay?amount=${amount}&orderId=${orderId}`,
      {},
    );
  }
}
