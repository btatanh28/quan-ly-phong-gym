import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class VnpayService {
  private apiUrl = `${API_CURRENT}`;

  constructor(private http: HttpClient) {}

  payVNPay(amount: number, orderId: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/vnpay/pay`, {
      amount,
      orderId,
    });
  }

  paymentReturn(params: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/vnpay/return`, {
      params,
    });
  }

  ipnVNPay(orderId: string, responseCode: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/vnpay/ipn`, {
      orderId,
      responseCode,
    });
  }
}
