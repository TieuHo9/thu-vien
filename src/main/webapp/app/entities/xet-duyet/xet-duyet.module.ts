import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { XetDuyetComponent } from './list/xet-duyet';
import { DeXuatThanhToanRoutingModule } from './route/de-xuat-thanh-toan-routing.module';
import { XetDuyetUpdateComponent } from './update/xet-duyet-update';

@NgModule({
  imports: [SharedModule, DeXuatThanhToanRoutingModule],
  declarations: [XetDuyetComponent, XetDuyetUpdateComponent],
  entryComponents: [],
})
export class XetDuyetModule {}
