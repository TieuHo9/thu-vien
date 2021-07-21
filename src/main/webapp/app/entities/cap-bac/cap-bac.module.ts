import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CapBacComponent } from './list/cap-bac.component';
import { CapBacDetailComponent } from './detail/cap-bac-detail.component';
import { CapBacUpdateComponent } from './update/cap-bac-update.component';
import { CapBacDeleteDialogComponent } from './delete/cap-bac-delete-dialog.component';
import { CapBacRoutingModule } from './route/cap-bac-routing.module';

@NgModule({
  imports: [SharedModule, CapBacRoutingModule],
  declarations: [CapBacComponent, CapBacDetailComponent, CapBacUpdateComponent, CapBacDeleteDialogComponent],
  entryComponents: [CapBacDeleteDialogComponent],
})
export class CapBacModule {}
