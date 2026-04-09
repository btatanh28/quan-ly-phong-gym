import { CheckInService } from './../../../../common/shared/service/application/CheckInService';
import { Html5QrcodeScanner } from 'html5-qrcode';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-scan-checkin',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './scan-checkin.component.html',
  styleUrls: ['./scan-checkin.component.css'],
})
export class ScanCheckinComponent implements OnInit {
  public scanResult: string = '';

  constructor(private checkInService: CheckInService) {}

  ngOnInit(): void {
    const scanner = new Html5QrcodeScanner(
      'reader',
      { fps: 10, qrbox: 250 },
      false,
    );

    scanner.render(
      (decodedText: string) => {
        if (this.scanResult) return;

        this.scanResult = decodedText;

        scanner.clear();

        this.checkin(decodedText);
      },
      (error: any) => {
        console.warn('QR error:', error);
      },
    );
  }

  async checkin(code: string) {
    const body = {
      qrCode: code,
      thietBi: 'WEB_CAMERA',
    };

    const response = await firstValueFrom(this.checkInService.checkIn(body));
    if (response) {
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Check-in thành công',
        showConfirmButton: false,
        timer: 2000,
      });

      setTimeout(() => {
          this.ngOnInit();
          this.scanResult = '';
        }, 3000);
    }
  }
}
