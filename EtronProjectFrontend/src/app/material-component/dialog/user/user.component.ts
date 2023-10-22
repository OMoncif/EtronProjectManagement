import { Component, EventEmitter, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoryComponent } from '../category/category.component';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {
  onAddUser = new EventEmitter();
  onEditUser = new EventEmitter();
  userForm:any = FormGroup;
  dialogAction:any = "Add";
  action:any = "Add";

  responseMessage:any;
  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  private userService:UserService,
  public dialogRef: MatDialogRef<CategoryComponent>,
  private snackbarService:SnackbarService) { }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      name:[null,[Validators.required]],
      prenom:[null,[Validators.required]],
      adresse:[null,[Validators.required]],
      contactnumber:[null,[Validators.required]],
      email:[null,[Validators.required , Validators.pattern(GlobalConstants.emailRegex)]],
      password:[null,[Validators.required]],
      role:[null,[Validators.required]],
      modeleVoiture:[null,[Validators.required]]
    });
    if (this.dialogData.action === 'Edit'){
      this.dialogAction = "Edit";
      this.action = "Update"
      this.userForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    }
    else {
      this.add();
    }
  }

  add() {
    var formData = this.userForm.value;
    var data = {
      name: formData.name,
      prenom: formData.prenom,
      adresse: formData.adresse,
      contactnumber: formData.contactnumber,
      email: formData.email,
      password: formData.password,
      role: formData.role,
      modeleVoiture: formData.modeleVoiture
    }
    this.userService.signUp(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddUser.emit();
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

  edit() {
    var formData = this.userForm.value;
    var data = {
      id:this.dialogData.data.id,
      name: formData.name,
      prenom: formData.prenom,
      adresse: formData.adresse,
      contactnumber: formData.contactnumber,
      email: formData.email,
      password: formData.password,
      role: formData.role,
      modeleVoiture: formData.modeleVoiture
    }
    this.userService.update(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddUser.emit();
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
