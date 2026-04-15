import pfp from './assets/pfp.jpeg';

function Card() {
    return (
        <div className="border border-black rounded-lg shadow-md p-5 m-2 text-center max-w-62.5 inline-block">
            <img
                src={pfp}
                alt="pfp"
                className="max-w-[60%] h-auto rounded-full mx-auto"
            />
            <h2 className="text-lg font-semibold m-">Nig</h2>
            <p className="text-sm mt-2">
                Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum
                lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum
            </p>
        </div>
    );
}

export default Card;