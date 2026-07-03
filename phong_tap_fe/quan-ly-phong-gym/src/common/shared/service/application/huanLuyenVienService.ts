import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';
@Injectable({
  providedIn: 'root',
})
export class HuanLuyenVienService {
  private apiUrl = `${API_CURRENT}/huan-luyen-vien`;

  constructor(private http: HttpClient) {}

  CreateHuanLuyenVien(huanLuyenVien: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, huanLuyenVien);
  }

  GetAllHuanLuyenVien(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getHuanLuyenVienById(id: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  UpdateHuanLuyenVien(huanLuyenVien: any): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/edit/${huanLuyenVien.id}`,
      huanLuyenVien,
    );
  }

  DeleteHuanLuyenVien(id: String): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
