import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';
@Injectable({
  providedIn: 'root',
})
export class ChiTietDonHangService {
  private apiUrl = `${API_CURRENT}/chi-tiet-don-hang`;

  constructor(private http: HttpClient) {}

  // getChiTietDonHangId(params: any): Observable<any> {
  //   Object.keys(params).forEach(
  //     (key) =>
  //       (params[key] == null || params[key] === '') && delete params[key],
  //   );
  //   return this.http.get<any>(`${this.apiUrl}/list`, { params });
  // }

  getChiTietDonHangId(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getChiTietDoanhThu(doanhThu: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/list/doanh-thu`, doanhThu);
  }
}
