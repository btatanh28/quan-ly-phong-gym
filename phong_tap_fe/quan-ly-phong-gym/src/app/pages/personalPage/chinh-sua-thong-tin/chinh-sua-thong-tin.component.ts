import { CustomerService } from './../../../../common/shared/service/application/customerService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  DialogMode,
  DialogService,
} from '../../../../common/shared/service/base/dialogservice';
import { ExtentionService } from '../../../../common/base/service/extention.service';
import { firstValueFrom } from 'rxjs';
import { API_CURRENT } from '../../../../common/shared/service/application/api-base';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-chinh-sua-thong-tin',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './chinh-sua-thong-tin.component.html',
  styleUrls: ['./chinh-sua-thong-tin.component.css'],
})
export class ChinhSuaThongTinComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public previewUrl: string | ArrayBuffer | null = null;
  public myForm?: FormGroup;
  public initForm: boolean = false;
  public indexTab: number = 0;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public modalImg: boolean = true;

  constructor(
    private dialogService: DialogService,
    private fb: FormBuilder,
    private ex: ExtentionService,
    private customerService: CustomerService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      soDienThoai: [null],
      email: [null],
      diaChi: [null],
      tenKhachHang: [null],
      hinhAnh: [null],
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
      this.customerService.getKhachHangById(this.id),
    );
    if (response) {
      this.myForm?.patchValue(response);
      if (response.hinhAnh) {
        this.previewUrl = API_CURRENT + response.hinhAnh;
        this.modalImg = false;
      }
    }
  }

  async saveData() {
    const req = {
      ...this.myForm?.getRawValue(),
    };

    let response = null;

    response = await firstValueFrom(this.customerService.updateKhachHang(req));

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

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = () => {
        this.previewUrl = reader.result;

        this.myForm?.patchValue({
          hinhAnh: reader.result, // 👈 gửi ảnh base64 trong JSON
        });

        this.modalImg = false;
      };

      reader.readAsDataURL(file); // chuyển thành base64
      this.modalImg = false;
    }
  }
}
