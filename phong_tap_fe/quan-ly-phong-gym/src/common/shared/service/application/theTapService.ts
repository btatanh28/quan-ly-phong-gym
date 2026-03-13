import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class TheTapService {
  private apiUrl = `${API_CURRENT}/the-tap`;

  constructor(private http: HttpClient) {}

  getAllTheTap(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getTheTapByKhachHang(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getCombobox(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/combobox`);
  }
}
