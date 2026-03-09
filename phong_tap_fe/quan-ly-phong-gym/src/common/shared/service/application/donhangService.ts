import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';
@Injectable({
  providedIn: 'root',
})
export class DonHangService {
  private apiUrl = `${API_CURRENT}/don-hang`;

  constructor(private http: HttpClient) {}

  CreateDonHang(donHang: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, donHang);
  }

  GetAllDonHang(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getDonHangById(id: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getDonHangByIdKhachHang(idKhachHang: String): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/khach-hang/${idKhachHang}`);
  }

  XacNhanDonHang(donHang: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/xac-nhan/${donHang.id}`, donHang);
  }

  DeleteDonHang(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
