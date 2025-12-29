import { useState } from "react";
import breakImage from "../assets/stickers/break.png";
import developerImage from "../assets/stickers/developer.png";
import products from "../data/products";
import BootstrapButton from "./BootstrapButton";
import PageHeading from "./PageHeading";
import ProductListings from "./ProductListings";

export default function Home() {
  const [isActive] = useState(() => Math.random() > 0.5);

  // const h1Styling = {
  //   textAlign: "center",
  //   color: isActive ? "black" : "red",
  // };

  return (
    <>
      {/* <h1
        classNameName={`my-heading ${
          isActive ? "primary-color" : "secondary-color"
        }`}
      >
        Demo of Global CSS Scope from Home
      </h1>
      <EazyButton>Home</EazyButton> */}
      {/* <h1 style={h1Styling}>Demo of Global CSS Scope from Home</h1> */}
      {/* <h1 classNameName="my-heading">Demo of Global CSS Scope from Home</h1> */}
      <div className="home-container">
        <div className="container col-6">
          <BootstrapButton text="Submit" type="primary" />
          <BootstrapButton text="Save" type="secondary" />
          <BootstrapButton text="Okay" type="success" />
          <BootstrapButton text="Cancel" type="danger" />
          <BootstrapButton text="Delete" type="warning" />
          <BootstrapButton text="Link" type="link" />
        </div>
        <div className="d-grid gap-2 col-8 mx-auto">
          <div className="alert alert-primary text-center" role="alert">
            A simple primary alert—check it out!
          </div>
          <div className="alert alert-secondary text-center" role="alert">
            A simple secondary alert—check it out!
          </div>
          <div className="alert alert-success text-center" role="alert">
            A simple success alert—check it out!
          </div>
          <div className="alert alert-danger text-center" role="alert">
            A simple danger alert—check it out!
          </div>
          <div className="alert alert-warning text-center" role="alert">
            A simple warning alert—check it out!
          </div>
          <div
            className="alert alert-warning alert-dismissible fade show text-center"
            role="alert"
          >
            <strong>Warning!</strong> This is a warning alert!
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="alert"
              aria-label="Close"
            ></button>
          </div>
        </div>
        <div className="container d-flex justify-content-center align-items-center gap-4">
          <div className="card" style={{ width: "18rem" }}>
            <img src={developerImage} className="card-img-top" alt="..." />
            <div className="card-body">
              <h5 className="card-title">Card title</h5>
              <p className="card-text">
                Some quick example text to build on the card title and make up
                the bulk of the card's content.
              </p>
              <a href="#" className="btn btn-primary">
                Go somewhere
              </a>
            </div>
          </div>
          <div className="card" style={{ width: "18rem" }}>
            <img src={breakImage} className="card-img-top" alt="..." />
            <div className="card-body">
              <h5 className="card-title">Card title</h5>
              <p className="card-text">
                Some quick example text to build on the card title and make up
                the bulk of the card's content.
              </p>
              <a href="#" className="btn btn-primary">
                Go somewhere
              </a>
            </div>
          </div>
        </div>
        <div className="row justify-content-center text-center mt-3 gap-3">
          <div className="col-3 border p-3 bg-warning">Column 1</div>
          <div className="col-3 border p-3 bg-success text-white">Column 2</div>
          <div className="col-3 border p-3 bg-danger text-white">Column 3</div>
        </div>
        <PageHeading title="Explore Eazy Stickers!">
          Add a touch of creativity to your space wih our wide range of fun and
          unique stickers. Perfect for any occasion!
        </PageHeading>
        <ProductListings products={products} />
      </div>
    </>
  );
}
