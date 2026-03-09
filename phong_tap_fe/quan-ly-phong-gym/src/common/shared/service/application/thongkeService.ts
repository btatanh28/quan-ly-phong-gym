import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { API_CURRENT } from "./api-base";

export interface ThongKe{
    Ngay: string,
    Thang: string,
    Nam: string,
    TuanTrongThang: string,
    DoanhThu: number,
    TongDonHang: number
}

@Injectable({
    providedIn: 'root'
})

export class ThongKeService{
    private apiUrl = `${API_CURRENT}/thongke`;

    constructor(private http: HttpClient){}

    doanhThuNgay(): Observable<any>{
        return this.http.get<any>(`${this.apiUrl}/doanhthu-ngay`);
    }

    doanhThuThang(): Observable<any>{
        return this.http.get<any>(`${this.apiUrl}/doanhthu-thang`);
    }

    doanhThuNam(): Observable<any>{
        return this.http.get<any>(`${this.apiUrl}/doanhthu-nam`)
    }
}
