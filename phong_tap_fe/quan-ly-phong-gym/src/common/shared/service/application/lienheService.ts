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

  getAllLienHe(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  createLienHe(lienHe: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, lienHe);
  }

  deleteLienHe(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
