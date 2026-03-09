import { ChiTietDonHangService } from './../../../../../common/shared/service/application/chiTietDonHang';
import { CommonModule } from '@angular/common';
import { FormModule } from '../../../../../common/module/forms.module';
import { DialogMode } from './../../../../../common/shared/service/base/dialogservice';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { trangThaiDonHang } from '../../../../../common/shared/enums/trangthaidonhang.enums';
import { firstValueFrom, Subscription } from 'rxjs';
import { DonHangService } from '../../../../../common/shared/service/application/donhangService';
import { AuthService } from '../../../../../common/shared/service/application/authService';
import Swal from 'sweetalert2';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';

@Component({
  selector: 'app-xac-nhan-don-hang',
  standalone: true,
  imports: [FormModule, CommonModule, InputSelectComponent],
  templateUrl: './xac-nhan-don-hang.component.html',
  styleUrls: ['./xac-nhan-don-hang.component.css'],
})
export class XacNhanDonHangComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public previewUrl: string | ArrayBuffer | null = null;
  public myForm?: FormGroup;
  public initForm: boolean = false;
  public indexTab: number = 0;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public listTrangThaiSanPham: any[] = trangThaiDonHang;
  public modalImg: boolean = true;
  private idNguoiDung: string | null = null;
  private currentUserSubscription!: Subscription;

  constructor(
    private fb: FormBuilder,
    private donHangService: DonHangService,
    private authService: AuthService,
    private chiTietDonHangService: ChiTietDonHangService,
  ) {
    this.myForm = fb.group({
      id: [null],
      trangThaiSanPham: [null],
      idNguoiDung: [null],
    });

    this.idNguoiDung = this.authService.getUserCurrent()?.id;

    if (!this.idNguoiDung) {
      this.myForm?.patchValue({
        idNguoiDung: this.idNguoiDung,
      });
    }

    this.currentUserSubscription =
      this.authService.currentUserSubject$.subscribe((user) => {
        this.idNguoiDung = user ? user.id : null;

        this.myForm?.patchValue({
          idNguoiDung: this.idNguoiDung,
        });
      });
  }

  async ngOnInit() {
    if (this.id) {
      await this.getData();
    }

    await this.handleModeDialog();

    this.initForm = true;
  }

  async handleModeDialog() {
    const modeDisableForm = [DialogMode.view];

    if (this.mode && modeDisableForm.includes(this.mode as DialogMode)) {
      this.myForm?.disable();
    }

    if (this.mode === DialogMode.add || this.mode === DialogMode.edit) {
      this.viewButtonSave = true;
    }
  }

  async getData() {
    const response = await firstValueFrom(
      this.donHangService.getDonHangById(this.id),
    );

    if (!response) return;

    // gán dữ liệu đơn hàng vào form
    this.myForm?.patchValue(response);

    // lấy chi tiết của đơn hàng
    const chiTietDonhangs = await firstValueFrom(
      this.chiTietDonHangService.getChiTietDonHangId(response.id),
    );

    // hiển thị chi tiết
    this.listOfData = chiTietDonhangs;
  }

  async saveData() {
    const req = {
      ...this.myForm?.getRawValue(),
      chiTietDonHangs: this.listOfData,
    };

    let response = null;

    if (this.mode === DialogMode.edit) {
      response = await firstValueFrom(this.donHangService.XacNhanDonHang(req));
    }

    Swal.fire({
      title: 'Lưu dữ liệu thành công!',
      icon: 'success',
      draggable: false,
    });

    this.closeDialog(true);
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }
}
