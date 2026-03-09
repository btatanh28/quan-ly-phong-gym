import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class VerifyService {
  private apiUrl = `${API_CURRENT}`;

  constructor(private http: HttpClient) {}

  verifyEmail(khachHang: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/khach-hang/verify`, khachHang);
  }
}
