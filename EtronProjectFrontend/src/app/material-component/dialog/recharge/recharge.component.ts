import { Component, EventEmitter, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoryComponent } from '../category/category.component';
import { RechargeService } from 'src/app/services/recharge.service';

@Component({
  selector: 'app-recharge',
  templateUrl: './recharge.component.html',
  styleUrls: ['./recharge.component.scss']
})
export class RechargeComponent {
  onAddRecharge = new EventEmitter();
  onEditRecharge = new EventEmitter();
  rechargeForm:any = FormGroup;
  dialogAction:any = "Add";
  action:any = "Add";

  responseMessage:any;
  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  private rechargeService:RechargeService,
  public dialogRef: MatDialogRef<CategoryComponent>,
  private snackbarService:SnackbarService) { }

  ngOnInit(): void {
    this.rechargeForm = this.formBuilder.group({
      quantiteEnergie:[null,[Validators.required]],
      typeCharge:[null,[Validators.required]],
      DureeRecharge:[null,[Validators.required]]
      
    });
    if (this.dialogData.action === 'Edit'){
      this.dialogAction = "Edit";
      this.action = "Update"
      this.rechargeForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {
   this.add();    
  }

  add() {
    var formData = this.rechargeForm.value;
    var data = {
      quantiteEnergie: formData.quantiteEnergie,
      typeCharge: formData.typeCharge,
      DureeRecharge: formData.DureeRecharge
      
    }
    this.rechargeService.add(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddRecharge.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error)=>{
      this.dialogRef.close();
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    });
  }

  
}
