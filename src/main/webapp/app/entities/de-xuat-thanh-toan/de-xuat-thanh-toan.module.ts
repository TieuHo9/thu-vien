import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DeXuatThanhToanComponent } from './list/de-xuat-thanh-toan.component';
import { DeXuatThanhToanDetailComponent } from './detail/de-xuat-thanh-toan-detail.component';
import { DeXuatThanhToanUpdateComponent } from './update/de-xuat-thanh-toan-update.component';
import { DeXuatThanhToanDeleteDialogComponent } from './delete/de-xuat-thanh-toan-delete-dialog.component';
import { DeXuatThanhToanRoutingModule } from './route/de-xuat-thanh-toan-routing.module';

@NgModule({
  imports: [SharedModule, DeXuatThanhToanRoutingModule],
  declarations: [
    DeXuatThanhToanComponent,
    DeXuatThanhToanDetailComponent,
    DeXuatThanhToanUpdateComponent,
    DeXuatThanhToanDeleteDialogComponent,
  ],
  entryComponents: [DeXuatThanhToanDeleteDialogComponent],
})
export class DeXuatThanhToanModule {}
