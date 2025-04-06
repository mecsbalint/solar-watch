import { useEffect, useState } from "react";

function UserForm({submitText, onSubmit, nameErrorMsg, passwordErrorMsg}) {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [nameErrorState, setNameErrorState] = useState(false);
  const [passwordErrorState, setPasswordErrorState] = useState(false);

  useEffect(() => {
    nameErrorMsg && setNameErrorState(true);
    passwordErrorMsg && setPasswordErrorState(true);
  }, [nameErrorMsg, passwordErrorMsg]);
  
  return (
    <form onSubmit={(e) => onSubmit(e, {name: name, password: password})}>
      <div className="my-5">
        <label className="fieldset-label">Name</label>
        <input
          type="text"
          className={`input ${nameErrorState && "input-error"}`}
          placeholder="username"
          onChange={(event) => {
            setName(event.target.value);
            setNameErrorState(false);
          }}
          value={name}
        />
        <p className="text-error">{nameErrorState ? nameErrorMsg : ""}</p>
      </div>

      <div className="my-5">
        <label className="fieldset-label">Password</label>
        <input
          type="password"
          className={`input ${passwordErrorState && "input-error"}`}
          placeholder="password"
          onChange={(event) => {
            setPassword(event.target.value);
            setPasswordErrorState(false);
          }}
          value={password}
        />
        <p className="text-error">{passwordErrorState ? passwordErrorMsg : ""}</p>
      </div>

      <button type="submit" className="btn btn-neutral mt-4">
        {submitText}
      </button>
    </form>
  );
}

export default UserForm;
