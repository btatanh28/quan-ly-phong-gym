import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = `${API_CURRENT}`;

  // BehaviorSubject để giữ trạng thái login
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  // Observable cho người dùng hiện tại
  private currentUserSubject = new BehaviorSubject<any>(
    this.getUserCurrent()?.user || null,
  );

  // private currentUser: { IDKhachHang: number; email: string; role: string } | null = null;

  constructor(private http: HttpClient) {
    this.checkTokenExpiration();
    // this.startTokenWatcher();
  }

  // Check xem localStorage có token không (lúc app khởi động)
  private hasToken(): boolean {
    if (
      typeof window === 'undefined' ||
      typeof window.localStorage === 'undefined'
    ) {
      return false; // Nếu không phải môi trường client (trình duyệt), không sử dụng localStorage
    }
    return !!localStorage.getItem('Token');
  }

  isLoggedIn$ = this.loggedIn.asObservable();
  currentUserSubject$ = this.currentUserSubject.asObservable();

  private decodeToken(token: string): any {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      return null;
    }
  }

  private isTokenExpired(): boolean {
    const expStr = localStorage.getItem('TokenExp');
    if (!expStr) return true;

    const exp = Number(expStr); // giây
    const now = Math.floor(Date.now() / 1000);

    return exp < now;
  }

  // private startTokenWatcher() {
  //   setInterval(() => {
  //     this.checkTokenExpiration();
  //   }, 1000 * 60); // kiểm tra mỗi 1 phút
  // }

  public checkTokenExpiration(): void {
    const token = this.getToken();
    if (!token) {
      this.loggedIn.next(false);
      return;
    }

    if (this.isTokenExpired()) {
      this.logOut();
    }
  }

  private setToken(token: string): void {
    localStorage.setItem('Token', token);

    const decoded = this.decodeToken(token);
    if (decoded?.exp) {
      localStorage.setItem('TokenExp', decoded.exp.toString());
    }
  }

  setCurrentUser(users: any): void {
    localStorage.setItem('Current', JSON.stringify(users));
    this.currentUserSubject.next(users);
  }

  login(user: any): Observable<any> {
    this.checkTokenExpiration();

    return this.http.post<any>(`${this.apiUrl}/auth/login`, user).pipe(
      tap((response) => {
        if (response?.token) {
          this.setToken(response.token);
          this.loggedIn.next(true);
        }
        const userData = {
          id: response.id,
          role: response.role,
          name: response.name,
          soDienThoai: response.soDienThoai,
          diaChi: response.diaChi,
        };
        if (userData) {
          this.setCurrentUser(userData);
        }
      }),
    );
  }

  changePassWord(users: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/new-password`, users);
  }

  register(users: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/register`, users);
  }

  registerClient(khachHang: any): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/khach-hang/register-client`,
      khachHang,
    );
  }

  isAdmin(): boolean {
    const user = this.getUserCurrent();
    return user?.role === 1 || user?.role === 2;
  }

  isEmployee(): boolean {
    const employee = this.getUserCurrent();
    return employee?.role === 5;
  }

  getUserCurrent() {
    if (
      typeof window !== 'undefined' &&
      typeof window.localStorage !== 'undefined'
    ) {
      const userData = localStorage.getItem('Current');
      if (userData) {
        try {
          const user = JSON.parse(userData);
          const id = user.id || null;
          const role = user.role || null;
          const name = user.name || null;
          const soDienThoai = user.soDienThoai || null;
          const diaChi = user.diaChi || null;
          return { user, id, role, name, soDienThoai, diaChi };
        } catch (e) {
          return { user: null, id: null, role: null, name: null };
        }
      }
    }
    return { user: null, id: null, role: null, name: null };
  }

  getToken(): string | null {
    return localStorage.getItem('Token');
  }

  logOut(): void {
    localStorage.clear();
    this.currentUserSubject.next(null);
    this.loggedIn.next(false);
  }
}
