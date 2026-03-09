// src/app/shared/shared.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Angular Forms
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Ng-Zorro Modules
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzSkeletonModule } from 'ng-zorro-antd/skeleton';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzCardModule } from 'ng-zorro-antd/card';
import { PagingComponent } from './paging/paging.component';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';

const NG_ZORRO_MODULES = [
  NzButtonModule,
  NzModalModule,
  NzInputModule,
  NzSelectModule,
  NzTableModule,
  NzTabsModule,
  NzIconModule,
  NzFormModule,
  NzDatePickerModule,
  NzRadioModule,
  NzCheckboxModule,
  NzTimePickerModule,
  NzToolTipModule,
  NzSpinModule,
  NzCollapseModule,
  NzDropDownModule,
  NzSkeletonModule,
  NzGridModule,
  NzUploadModule,
  NzCardModule,
  NzIconModule,
  NzLayoutModule,
  NzMenuModule,
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    PagingComponent,
    ReactiveFormsModule,
    ...NG_ZORRO_MODULES,
  ],
  exports: [
    CommonModule,
    FormsModule,
    PagingComponent,
    ReactiveFormsModule,
    ...NG_ZORRO_MODULES,
  ],
})
export class FormModule {}
