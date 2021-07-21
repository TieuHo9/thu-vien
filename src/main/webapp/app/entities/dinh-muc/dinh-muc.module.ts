import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DinhMucComponent } from './list/dinh-muc.component';
import { DinhMucDetailComponent } from './detail/dinh-muc-detail.component';
import { DinhMucUpdateComponent } from './update/dinh-muc-update.component';
import { DinhMucDeleteDialogComponent } from './delete/dinh-muc-delete-dialog.component';
import { DinhMucRoutingModule } from './route/dinh-muc-routing.module';

@NgModule({
  imports: [SharedModule, DinhMucRoutingModule],
  declarations: [DinhMucComponent, DinhMucDetailComponent, DinhMucUpdateComponent, DinhMucDeleteDialogComponent],
  entryComponents: [DinhMucDeleteDialogComponent],
})
export class DinhMucModule {}
