import { Html5QrcodeScanner } from 'html5-qrcode';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-scan-checkin',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './scan-checkin.component.html',
  styleUrls: ['./scan-checkin.component.css'],
})
export class ScanCheckinComponent implements OnInit {
  scanResult: string = '';

  ngOnInit(): void {
    const scanner = new Html5QrcodeScanner(
      'reader',
      { fps: 10, qrbox: 250 },
      false,
    );

    scanner.render(
      (decodedText: string) => {
        console.log('QR:', decodedText);
        this.scanResult = decodedText;

        // gọi API checkin
        this.checkin(decodedText);
      },
      (error: any) => {
        console.warn('QR error:', error);
      },
    );
  }

  checkin(code: string) {
    console.log('Checkin:', code);
  }
}
