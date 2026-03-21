import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class ContactService {
  private apiUrl = `${API_CURRENT}/lien-he`;

  constructor(private http: HttpClient) {}

  getAllDatBan(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  getDatBanById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getDatBanByIdKhachHang(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/khachhang/${id}`);
  }

  createLienHe(lienHe: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, lienHe);
  }

  updateDatBan(id: number, DatBanData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, DatBanData);
  }

  deleteDatBan(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  XacNhanDatBan(
    id: number,
    DataTrangThai: { TrangThaiDatBan: string },
  ): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/xacnhan/${id}`, DataTrangThai, {
      headers: { 'Content-Type': 'application/json' },
    });
  }
}
