import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { API_CURRENT } from './api-base';

export interface KhachHang {
  IDKhachHang: number;
  TenKhachHang: string;
  MatKhau: string;
  SoDienThoai: string;
  Email: string;
  DiaChi: string;
  HinhAnh: string;
}

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private apiUrl = `${API_CURRENT}`;

  constructor(private http: HttpClient) {}

  getAllKhachHang(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/khach-hang/list`, { params });
  }

  getKhachHangById(id: String): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/khach-hang/${id}`);
  }

  updateKhachHang(khachhang: any): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/khach-hang/edit/${khachhang.id}`,
      khachhang,
    );
  }

  deleteKhachHang(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/khach-hang/${id}`);
  }
}
